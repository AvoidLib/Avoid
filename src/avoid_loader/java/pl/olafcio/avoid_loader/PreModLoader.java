package pl.olafcio.avoid_loader;

import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.subloaders.ILoader;
import pl.olafcio.avoid_loader.subloaders.ITransformer;
import pl.olafcio.avoid_platform.AvoidWrappedLoader;
import pl.olafcio.avoid_platform.PMLImpl;

import javax.script.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public final class PreModLoader {
    private PreModLoader() {}

    static final Logger LOGGER
               = LoggerFactory.getLogger("AvoidLoader");

    private static final ArrayList<PreModContainer> MODS
                   = new ArrayList<>();

    /**
     * Collects mods to load.
     */
    public static void preload() {
        try {
            var loadedMods = Set.<Path>of();

            loadFrom(Path.of("mods"), loadedMods);
            loadFrom(Path.of("plugins"), loadedMods);
            loadFrom(Path.of("avoidmods"), loadedMods);
            loadFrom(Path.of("avoidplugins"), loadedMods);
            loadFrom(Path.of("avoidaddons"), loadedMods);

            LOGGER.info("Loading {} addons:{}", MODS.size(), MODS.stream()
                                                                 .filter(mod -> mod.parent() == null)
                                                                 .map(mod -> "\n   - " + PreModLoader.getModString(mod, 1))
                                                                 .collect(Collectors.joining("")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to enumerate Avoid mods", e);
        }

        for (var mod : MODS) {
            if (mod.manifest().has("loaders")) {
                var subloaders = mod.manifest().get("loaders").getAsJsonArray();

                try {
                    for (var cn : subloaders) {
                        var pluginClass = mod.classLoader().loadClass(cn.getAsString());
                        var plugin = (ILoader) pluginClass.getDeclaredConstructor().newInstance();

                        plugin.load();

                        for (var mod2 : MODS)
                            plugin.processAvoidMod(mod2);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /ClassNotFoundException".formatted(mod.path().toAbsolutePath()), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /InvocationTargetException".formatted(mod.path().toAbsolutePath()), e);
                } catch (InstantiationException e) {
                    throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /InstantiationException".formatted(mod.path().toAbsolutePath()), e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /IllegalAccessException\n  > Is your subloader's constructor and class public?".formatted(mod.path().toAbsolutePath()), e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /NoSuchMethodException\n  > Does your subloader have a default constructor?".formatted(mod.path().toAbsolutePath()), e);
                }
            }
        }
    }

    @NotNull
    private static String getModString(PreModContainer mod, int tab) {
        var text = new StringBuilder();

        text.append(mod.manifest().get("name").getAsString() + " " +
                    mod.manifest().get("version").getAsString() + " " +
                    mod.additionalText().orElse(""));

        for (var child : mod.children()) {
            text.append("\n   ")
                .append(" ".repeat(tab))
                .append("\\_ ")
                .append(getModString(child, tab + 1));
        }

        return text.toString();
    }

    public static List<PreModContainer> getMods() {
        return MODS;
    }

    /**
     * Adds a path to the game classpath.
     * @param path Usually a path to a JAR file.
     */
    public static void addToClasspath(Path path) {
        PMLImpl.addToClasspath(path);
    }

    /**
     * Adds a class transformer.
     */
    public static void addTransformer(ITransformer transformer) {
        PMLImpl.addTransformer(transformer);
    }

    private static void loadFrom(Path modsDir, Set<Path> loadedMods)
                   throws IOException
    {
        if (!Files.isDirectory(modsDir))
            return;

        try (var mods = Files.list(modsDir)) {
            mods.forEach(mod -> {
                if ((mod.toString().endsWith(".jar") || mod.toString().endsWith(".avoid.zip")) && !loadedMods.contains(mod)) {
                    new ModParser(mod, loadedMods, MODS, null).load();
                }
            });
        }

        var completed = new ArrayList<PreModContainer>();

        while (true) {
            var finished = true;
            var copy = (List<PreModContainer>) MODS.clone();

            for (var mod : copy) {
                if (!completed.contains(mod)) {
                    if (extractJIJ(mod, loadedMods))
                        finished = false;

                    try {
                        var hook = mod.classLoader().loadClass("avoid_hooks.PreList");
                        if (!IHook.class.isAssignableFrom(hook)) {
                            LOGGER.warn("Fatal error applying PreList hook from mod '{}'", mod.manifest().get("id").getAsString());
                            throw new LoaderHookException("Failed to apply 'PreList' on '%s': doesn't implement IHook".formatted(mod.path().toAbsolutePath()));
                        }

                        try {
                            ((IHook) hook.getDeclaredConstructor().newInstance()).run();
                        } catch (Throwable e) {
                            LOGGER.warn("Fatal error applying PreList hook from mod '{}'", mod.manifest().get("id").getAsString());
                            throw new LoaderHookException("Failed to apply 'PreList' on '%s': %s".formatted(mod.path().toAbsolutePath(), e.getMessage()), e);
                        }
                    } catch (ClassNotFoundException ignored) {}

                    completed.add(mod);
                }
            }

            if (finished)
                break;
        }
    }

    private static boolean extractJIJ(PreModContainer container, Set<Path> loadedMods) throws IOException {
        if (container.manifest().has("jars")) {
            var jars = container.manifest().get("jars").getAsJsonArray();
            var folder = Path.of(".cache/avoid_loader/embedded_jars");

            try                   { Files.createDirectories(folder);                                                      }
            catch (IOException e) { throw new RuntimeException("Failed to create Avoid embedded jar cache directory", e); }

            for (var embeddedJAR : jars) {
                String jarPath;

                boolean library;
                boolean mod;

                if (embeddedJAR.isJsonObject()) {
                    var obj = (JsonObject) embeddedJAR;

                    jarPath = obj.get("path").getAsString();

                    library = !obj.has("library") ? true : obj.get("library").getAsBoolean();
                    mod     = !obj.has("mod")     ? true : obj.get(  "mod"  ).getAsBoolean();
                } else {
                    jarPath = embeddedJAR.getAsString();

                    library = true;
                    mod     = true;
                }

                Path path;

                try (var jar = new JarFile(container.path().toFile())) {
                    var entry = jar.getJarEntry(jarPath);

                    try (var stream = jar.getInputStream(entry)) {
                        path = folder.resolve(DigestUtils.sha1Hex(stream) + ".jar");
                    }

                    try (var fos = new FileOutputStream(path.toFile())) {
                        try (var stream = jar.getInputStream(entry)) {
                            stream.transferTo(fos);
                        }
                    }
                }

                if (mod) {
                    if (library) {
                        container.classLoader().addURL(path.toUri().toURL());

                        container.children().add(new ModParser(path, loadedMods, MODS, container.classLoader(), container).load());
                    } else {
                        container.children().add(new ModParser(path, loadedMods, MODS, container).load());
                    }
                } else {
                    if (!library)
                        throw new RuntimeException("Jar-in-jar entry with library:false & mod:false; cannot load into void");

                    container.classLoader().addURL(path.toUri().toURL());
                }
            }

            return true;
        }

        return false;
    }
}
