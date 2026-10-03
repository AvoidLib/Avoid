package pl.olafcio.avoid_loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.mods.loader.subloader.ILoader;
import pl.olafcio.avoid.mods.loader.subloader.ITransformer;
import pl.olafcio.avoid_impl.PMLImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
            var loadedMods = AvoidWrappedLoader.getModsPaths();

            loadFrom(AvoidWrappedLoader.getGameDir().resolve("mods"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("plugins"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidmods"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidplugins"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidaddons"), loadedMods);

            LOGGER.info("Loading {} addons:{}", MODS.size(), MODS.stream()
                                                                 .map(mod -> mod.manifest().get("name").getAsString() + " " +
                                                                                            mod.manifest().get("version").getAsString())
                                                                 .map(text -> "\n - " + text)
                  .collect(Collectors.joining("")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to enumerate Avoid mods", e);
        }

        for (var mod : MODS) {
            if (mod.manifest().has("loaders")) {
                var subloaders = mod.manifest().get("loaders").getAsJsonArray();

                try (var classLoader = new URLClassLoader(new URL[]{ mod.path().toUri().toURL() }, PreModLoader.class.getClassLoader()) {
                    @Override
                    protected Class<?> loadClass(String name, boolean resolve)
                       throws ClassNotFoundException
                    {
                        if (name.startsWith("net.minecraft."))
                            throw new ClassNotFoundException("Cannot load Minecraft classes during subloading!");

                        return super.loadClass(name, resolve);
                    }
                }) {
                    for (var cn : subloaders) {
                        var pluginClass = classLoader.loadClass(cn.getAsString());
                        var plugin = (ILoader) pluginClass.getDeclaredConstructor().newInstance();

                        plugin.load();

                        for (var mod2 : MODS)
                            plugin.processAvoidMod(mod2);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /IOException".formatted(mod.path().toAbsolutePath()), e);
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
                    new ModParser(mod, loadedMods, MODS).load();
                }
            });
        }
    }
}
