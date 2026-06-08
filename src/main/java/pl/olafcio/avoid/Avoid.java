package pl.olafcio.avoid;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.mods.AvoidModMeta;
import pl.olafcio.avoid.mods.annotation_processor.OverwriteScreen;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.Screens;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Avoid implements ModInitializer {
    @ApiStatus.Internal
    public static Minecraft mc;

    @ApiStatus.Internal
    public static final Logger LOGGER
                      = LogUtils.getLogger();

    @ApiStatus.Internal
    private static final Gson GSON
                   = new Gson();

    @Override
    public void onInitialize() {
        try {
            var modsDir = FabricLoader.getInstance().getGameDir().resolve("mods");
            var loadedMods = FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getRootPaths)
                                                                             .flatMap(Collection::stream)
                                                                    .collect(Collectors.toSet());

            var avoidMods = new ArrayList<String>();

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

                                    var klass = classLoader.loadClass(className);
                                    if (klass.isAnnotationPresent(OverwriteScreen.class)) {
                                        if (!Screen.class.isAssignableFrom(klass)) {
                                            LOGGER.warn("@OverwriteScreen requires the annotated type to extend Screen (avoid.net.screen)");
                                            continue;
                                        }

                                        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
                                            LOGGER.warn("@OverwriteScreen({}) on server, skipping", klass.getDeclaredAnnotation(OverwriteScreen.class).value().name());
                                            continue;
                                        }

                                        //noinspection unchecked
                                        Screens.overwrite(
                                                klass.getDeclaredAnnotation(OverwriteScreen.class).value(),
                                                (Class<? extends Screen>) klass
                                        );
                                    }
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
                                                        klass);

                            ///TIP: Access mod metadata by using {@link AvoidManager#getLoadedAddons()}
                            AvoidManager.metadatas.put(id, meta);

                            var constructor = klass.getDeclaredConstructor();
                            constructor.setAccessible(true);

                            var instance = constructor.newInstance();
                            instance.onEnable();

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

            LOGGER.info("Loaded {} addons:{}", avoidMods.size(), avoidMods.stream().map(text -> "\n - " + text)
                                                                          .collect(Collectors.joining("")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to enumerate Avoid mods", e);
        }
    }
}
