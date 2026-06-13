package pl.olafcio.avoid;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.mods.AvoidModMeta;
import pl.olafcio.avoid.mods.ModEnvironment;
import pl.olafcio.avoid.mods.annotation_processor.AutoCommand;
import pl.olafcio.avoid.mods.annotation_processor.OverwriteScreen;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.block.values.NoteBlockInstrument;
import pl.olafcio.avoid.net.command.Command;
import pl.olafcio.avoid.net.command.CommandManager;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.Screens;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Avoid {
    @ApiStatus.Internal
    public static final Logger LOGGER
                      = LogUtils.getLogger();

    @ApiStatus.Internal
    private static final Gson GSON
                   = new Gson();

    @ApiStatus.Internal
    public static final Avoid INSTANCE
                  = new Avoid();

    private Avoid() {}

    private boolean Initialized = false;
    private final List<Runnable> Initializers
            = new ArrayList<>();

    private void Schedule(Runnable code) {
        if (Initialized)
            code.run();
        else Initializers.add(code);
    }

    private void Realize() {
        for (var init : Initializers)
            init.run();

        Initialized = true;
    }

    public void onInitialize() {
        NoteBlockInstrument.clinit();
        Realize();

        // TODO: Call a method on all mods
    }

    public void onEarlyInit() {
        try {
            var loadedMods = AvoidWrappedLoader.getModsPaths();
            var avoidMods = new ArrayList<String>();

            loadFrom(AvoidWrappedLoader.getGameDir().resolve("mods"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("plugins"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidmods"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidplugins"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidaddons"), loadedMods, avoidMods);

            LOGGER.info("Loaded {} addons:{}", avoidMods.size(), avoidMods.stream().map(text -> "\n - " + text)
                                                                          .collect(Collectors.joining("")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to enumerate Avoid mods", e);
        }
    }

    private void loadFrom(Path modsDir, Set<Path> loadedMods, ArrayList<String> avoidMods) throws IOException {
        if (!Files.isDirectory(modsDir))
            return;

        try (var mods = Files.list(modsDir)) {
            mods.forEach(mod -> {
                if (mod.toString().endsWith(".jar") && !loadedMods.contains(mod)) {
                    try (var jar = new JarFile(mod.toFile())) {
                        var manifestFile = jar.getEntry("avoid.mod.json");
                        if (manifestFile == null)
                            return;

                        byte[] manifestData = jar.getInputStream(manifestFile)
                                                 .readAllBytes();

                        var manifest = GSON.fromJson(new String(manifestData), JsonObject.class);
                        if (manifest == null) {
                            LOGGER.error("Failed to read Avoid mod manifest: {}", mod.toAbsolutePath());
                            return;
                        }

                        int schema = manifest.get("__schema").getAsInt();
                        if (schema != 1) {
                            LOGGER.error("Invalid Avoid mod manifest __schema: {} [{}]", schema, mod.toAbsolutePath());
                            return;
                        }

                        String id            = manifest.get("id").getAsString();
                        String version       = manifest.get("version").getAsString();
                        String versionSystem = manifest.get("version-system").getAsString();

                        String name        = manifest.get("name").getAsString();
                        String author      = manifest.get("author").getAsString();
                        String description = manifest.get("description").getAsString();

                        ModEnvironment env = manifest.has("environment")
                                                ? ModEnvironment.valueOf(manifest.get("environment").getAsString().toUpperCase())
                                                : ModEnvironment.ALL;

                        if (env == ModEnvironment.CLIENT) {
                            if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT) {
                                LOGGER.warn("Skipping client-only mod: {} [{}]", name, id);
                                return;
                            }
                        } else if (env == ModEnvironment.SERVER) {
                            if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.SERVER) {
                                LOGGER.warn("Skipping server-only mod: {} [{}]", name, id);
                                return;
                            }
                        }

                        var classLoader = URLClassLoader.newInstance(
                                new URL[]{ mod.toUri().toURL() },
                                Avoid.class.getClassLoader()
                        );

                        String klassName = manifest.get("main-class").getAsString();
                        Class<?> klassUnc = classLoader.loadClass(klassName);

                        var entries = jar.entries();
                        do {
                            var el = entries.nextElement();
                            var fn = el.getRealName();
                            if (!el.isDirectory() && fn.endsWith(".class")) {
                                var className = fn.substring(0, fn.length() - 6)
                                                  .replace("/", ".");

                                Class<?> klass;

                                try {
                                    klass = classLoader.loadClass(className);
                                } catch (Exception e) {
                                    LOGGER.error("Failed to load class {} ({})", className, mod.getFileName().toString());
                                    continue;
                                }

                                if (klass.isAnnotationPresent(OverwriteScreen.class)) {
                                    if (!Screen.class.isAssignableFrom(klass)) {
                                        LOGGER.error("@OverwriteScreen requires the annotated type to extend Screen (avoid.net.screen)");
                                        continue;
                                    }

                                    if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT) {
                                        LOGGER.debug("@OverwriteScreen({}) on server, skipping", klass.getDeclaredAnnotation(OverwriteScreen.class).value().name());
                                        continue;
                                    }

                                    LOGGER.debug("Scheduling screen overwrite: {} ({})", className, mod.getFileName().toString());

                                    Schedule(() -> {
                                        //noinspection unchecked
                                        Screens.overwrite(
                                                klass.getDeclaredAnnotation(OverwriteScreen.class).value(),
                                                (Class<? extends Screen>) klass
                                        );
                                    });
                                }

                                if (klass.isAnnotationPresent(AutoCommand.class)) {
                                    if (!Command.class.isAssignableFrom(klass)) {
                                        LOGGER.error("@AutoCommand requires the annotated type to extend Command (avoid.net.command)");
                                        continue;
                                    }

                                    CommandManager.add((Command) klass.getDeclaredConstructor().newInstance());
                                }

                                EventManager.collect(klass);
                            }
                        } while (entries.hasMoreElements());

                        if (!AvoidMod.class.isAssignableFrom(klassUnc)) {
                            LOGGER.error("Main class of mod must extend AvoidMod: {}", mod.toAbsolutePath());
                            return;
                        }

                        //noinspection unchecked
                        var klass = (Class<? extends AvoidMod>)
                                     klassUnc;

                        var meta = new AvoidModMeta(id, version, versionSystem,
                                                    name, author, description,
                                                    env, klass);

                        ///TIP: Access mod metadata by using {@link AvoidManager#getLoadedAddons()}
                        AvoidManager.metadatas.put(id, meta);

                        var constructor = klass.getDeclaredConstructor();
                        constructor.setAccessible(true);

                        var instance = constructor.newInstance();
                        instance.onLoad();

                        Schedule(instance::onEnable);

                        avoidMods.add(meta.name() + " " + meta.version());
                    } catch (IOException e) {
                        LOGGER.error("Failed to read mod .jar file [IOException]: {}", mod.toAbsolutePath(), e);
                    } catch (NullPointerException e) {
                        LOGGER.error("Failed to read mod .jar avoid-manifest [NullPE]: {}", mod.toAbsolutePath(), e);
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                             InstantiationException | IllegalAccessException e)
                    {
                        LOGGER.error("Failed to enable Avoid mod: {}", mod.toAbsolutePath(), e);
                    }
                }
            });
        }
    }
}
