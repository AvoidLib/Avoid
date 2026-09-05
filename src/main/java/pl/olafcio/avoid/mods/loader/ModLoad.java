package pl.olafcio.avoid.mods.loader;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import pl.olafcio.avoid.*;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.mods.AvoidModBase;
import pl.olafcio.avoid.mods.AvoidModMeta;
import pl.olafcio.avoid.mods.ModEnvironment;
import pl.olafcio.avoid.mods.annotation_processor.*;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.mods.events_loader.ModLoadedEvent;
import pl.olafcio.avoid.mods.events_loader.ModEnablingEvent;
import pl.olafcio.avoid.mods.events_loader.error.ModErrorEvent;
import pl.olafcio.avoid.mods.events_loader.error.ModFatalEvent;
import pl.olafcio.avoid.mods.events_loader.scanning.ModClassAnalyzingEvent;
import pl.olafcio.avoid.mods.events_loader.scanning.ModClassCollectingEvent;
import pl.olafcio.avoid.mods.loader.mod.*;
import pl.olafcio.avoid.mods.loader.mod_method.LXKeyHandler;
import pl.olafcio.avoid.net.block.Block;
import pl.olafcio.avoid.net.block.Blocks;
import pl.olafcio.avoid.net.id.Identification;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@ApiStatus.Internal
public final class ModLoad
             implements LXItem, LXScreenOverwrite, LXScreenModifier, LXCommand, LXSelector, LXEntity, LXFluid, LXFog, LXEffect,
                        LXKeyHandler
{
    @ApiStatus.Internal
    private static final Gson GSON
                   = new Gson();

    private final Path mod;
    private final Set<Path> loadedMods;
    private final ArrayList<String> avoidMods;

    public ModLoad(Path mod, Set<Path> loadedMods, ArrayList<String> avoidMods) {
        this.mod = mod;
        this.loadedMods = loadedMods;
        this.avoidMods = avoidMods;
    }

    public void load() {
        try (var jar = new JarFile(mod.toFile())) {
            var manifestFile = jar.getEntry("avoid.mod.json");
            if (manifestFile == null)
                return;

            byte[] manifestData = jar.getInputStream(manifestFile)
                    .readAllBytes();

            var manifest = GSON.fromJson(new String(manifestData), JsonObject.class);
            if (manifest == null) {
                Avoid.LOGGER.error("Failed to read Avoid mod manifest: {}", mod.toAbsolutePath());
                return;
            }

            int schema = manifest.get("__schema").getAsInt();
            if (schema != 1) {
                Avoid.LOGGER.error("Invalid Avoid mod manifest __schema: {} [{}]", schema, mod.toAbsolutePath());
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

            List<String> libraries = manifest.has("libraries")
                                            ? manifest.getAsJsonArray("libraries")
                                                      .asList()
                                                      .stream()
                                                      .map(JsonElement::getAsString)
                                                      .toList()
                                            : List.of();

            if (env == ModEnvironment.CLIENT) {
                if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT) {
                    Avoid.LOGGER.warn("Skipping client-only mod: {} [{}]", name, id);
                    return;
                }
            } else if (env == ModEnvironment.SERVER) {
                if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.SERVER) {
                    Avoid.LOGGER.warn("Skipping server-only mod: {} [{}]", name, id);
                    return;
                }
            }

            var URLs = new ArrayList<URL>();

            final var mirror = "https://maven-central-eu.storage-download.googleapis.com/maven2/";
            final var http = HttpClient.newHttpClient();

            try {
                for (var lib : libraries) {
                    var parts = lib.split(":", 3);
                    var slash = parts[0].replace(".", "/")
                              + "/" + parts[1]
                              + "/" + parts[2].replace(":", "/")
                              + "/" + parts[1] + "-" + parts[2].replace(":", "/") + ".jar";

                    var libpath = Path.of("libraries/" + slash);

                    URLs.add(libpath.toUri().toURL());

                    if (!Files.isRegularFile(libpath)) {
                        Files.createDirectories(libpath.getParent());

                        var url = mirror + slash;

                        Avoid.LOGGER.info("[Library Downloader] Using '{}' (mod = '{}')", lib, id);

                        http.send(HttpRequest.newBuilder()
                                             .uri(URI.create(url))
                                             .build(), HttpResponse.BodyHandlers.ofFile(libpath));
                    }
                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException("AvoidLib failed to download maven dependencies (from mirror) for mod: '%s' (%s)".formatted(id, mod.toString()), e);
            } finally {
                http.close();
            }

            List<String> skipClasses = manifest.has("skip-classes")
                    ? manifest.get("skip-classes").getAsJsonArray().asList().stream()
                                                                            .map(JsonElement::getAsString)
                                                                            .toList()
                    : List.of();

            URLs.add(mod.toUri().toURL());

            var classLoader = URLClassLoader.newInstance(
                    URLs.toArray(URL[]::new),
                    Avoid.class.getClassLoader()
            );

            String klassName = manifest.get("main-class").getAsString();

            if (skipClasses.contains(klassName))
                Avoid.LOGGER.warn("Main class of mod cannot be skipped; loading anyways");

            Class<?> klassUnc = classLoader.loadClass(klassName);

            if (!AvoidMod.class.isAssignableFrom(klassUnc)) {
                Avoid.LOGGER.error("Main class of mod must extend AvoidMod: {}", mod.toAbsolutePath());
                return;
            }

            //noinspection unchecked
            var klass = (Class<? extends AvoidMod>)
                        klassUnc;

            var meta = new AvoidModMeta(id, version, versionSystem,
                                        name, author, description,
                                        env, libraries, klass);

            scanAllClasses(jar, classLoader, id, skipClasses, meta);

            ///TIP: Access mod metadata by using {@link AvoidModLoader#getLoadedAddons()}
            AvoidModLoader.metadatas.put(id, meta);
            AvoidModLoader.files.put(meta, mod);

            var constructor = klass.getDeclaredConstructor();
            constructor.setAccessible(true);

            var instance = constructor.newInstance();

            var metaField = AvoidModBase.class.getDeclaredField("meta");
            metaField.setAccessible(true);
            metaField.set(instance, meta);

            AvoidModLoader.instances.put(meta, instance);
            instance.onLoad();

            Avoid.INSTANCE.Schedule(() -> {
                EventManager.fire(new ModEnablingEvent(meta));
                instance.onEnable();
            });

            avoidMods.add(meta.name() + " " + meta.version());

            EventManager.fire(new ModLoadedEvent(meta));
        } catch (IOException e) {
            Avoid.LOGGER.error("Failed to read mod .jar file [IOException]: {}", mod.toAbsolutePath(), e);
            EventManager.fire(new ModErrorEvent(mod, e));
        } catch (NullPointerException e) {
            Avoid.LOGGER.error("Failed to read mod .jar avoid-manifest [NullPE]: {}", mod.toAbsolutePath(), e);
            EventManager.fire(new ModErrorEvent(mod, e));
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            Avoid.LOGGER.error("Failed to enable Avoid mod: {}", mod.toAbsolutePath(), e);
            EventManager.fire(new ModErrorEvent(mod, e));
        } catch (NoSuchFieldException e) {
            try {
                EventManager.fire(new ModFatalEvent(mod, e));
            } catch (Exception ex) {
                Avoid.LOGGER.warn("Failed to fire ModFatalEvent for '%s': ".formatted(mod.toAbsolutePath()), ex);
            }

            throw new RuntimeException("Failed to set Avoid mod metadata: %s".formatted(mod.toAbsolutePath()), e);
        }
    }

    private void scanAllClasses(JarFile jar, URLClassLoader classLoader, String id, List<String> skipClasses, AvoidModMeta meta)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        var classes = new HashMap<Class<?>, AtomicBoolean>();
        var entries = jar.entries();

        EventManager.fire(new ModClassCollectingEvent(meta));

        do {
            var el = entries.nextElement();
            var fn = el.getRealName();
            if (!el.isDirectory() && fn.endsWith(".class")) {
                var className = fn.substring(0, fn.length() - 6)
                                  .replace("/", ".");

                if (skipClasses.contains(className)) {
                    Avoid.LOGGER.debug("Skipping class: {}", className);
                    continue;
                }

                Class<?> klass;

                try {
                    klass = classLoader.loadClass(className);
                } catch (Exception e) {
                    Avoid.LOGGER.error("Failed to load class {} ({})", className, mod.getFileName().toString());
                    continue;
                }

                var usedAutoID = new AtomicBoolean(false);

                if (registerAutoFluid(id, klass, className, usedAutoID))
                    continue;

                classes.put(klass, usedAutoID);
            }
        } while (entries.hasMoreElements());

        EventManager.fire(new ModClassAnalyzingEvent(meta));

        for (var entry : classes.entrySet()) {
            var klass = entry.getKey();
            var className = klass.getName();

            if (registerScreenOverwrite(klass, className))
                continue;

            if (registerScreenModifier(klass, className))
                continue;

            if (registerAutoCommand(klass))
                continue;

            var usedAutoID = entry.getValue();

            if (registerAutoBlock(id, klass, className, usedAutoID))
                continue;

            if (registerAutoItem(id, klass, className, usedAutoID))
                continue;

            if (registerAutoEntity(id, klass, className, usedAutoID))
                continue;

            if (registerAutoEffect(id, klass, className, usedAutoID))
                continue;

            if (registerAutoFog(id, klass, className))
                continue;

            if (!usedAutoID.get() && klass.isAnnotationPresent(AutoID.class))
                Avoid.LOGGER.warn("@AutoID not applicable ({})", className);

            var usedAutoChar = new AtomicBoolean(false);

            if (registerAutoSelector(id, klass, className, usedAutoChar))
                continue;

            if (!usedAutoChar.get() && klass.isAnnotationPresent(AutoChar.class))
                Avoid.LOGGER.warn("@AutoChar not applicable ({})", className);

            Method[] methods;

            try {
                methods = klass.getDeclaredMethods();
            } catch (Exception e) {
                LOGGER.warn("Failed to collect methods from {}", klass.getName());
                return;
            }

            EventManager.collect(klass, methods);

            if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT) {
                for (var m : methods) {
                    if (m.isAnnotationPresent(KeyHandler.class)) {
                        registerKeyHandler(klass, m);
                    }
                }
            }
        }
    }

    private static final Logger LOGGER
                       = LogUtils.getLogger();

    private boolean registerAutoBlock(String id, Class<?> klass, String className, AtomicBoolean usedAutoID)
            throws NoSuchMethodException
    {
        if (klass.isAnnotationPresent(AutoBlock.class)) {
            if (!Block.class.isAssignableFrom(klass)) {
                Avoid.LOGGER.error("@AutoBlock requires the annotated type to extend Block (avoid.net.block)");
                return true;
            }

            if (!klass.isAnnotationPresent(AutoID.class)) {
                Avoid.LOGGER.error("@AutoBlock requires the annotated type to be also annotated with @AutoID");
                return true;
            }

            usedAutoID.set(true);

            var simpleName = klass.getSimpleName();

            suffixRemover:
            {
                if (!simpleName.endsWith("Block")) {
                    Avoid.LOGGER.warn("All block classes should end with 'Block', found non-matching: {} ({})", simpleName, className);
                    break suffixRemover;
                }

                simpleName = simpleName.substring(0, simpleName.length() - 5);
            }

            var constructor = klass.getDeclaredConstructor();
            var idstr = id + ":" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);

            Avoid.LOGGER.debug("Registering block '{}'", idstr);

            var interceptor = new AvoidPackageOnly<net.minecraft.world.level.block.Block>();

            Blocks.register(Identification.of(idstr), () -> {
                try {
                    return (Block) constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to construct block (%s)".formatted(idstr), e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }, interceptor);

            if (klass.isAnnotationPresent(AutoBlockItem.class)) {
                Avoid.LOGGER.debug("Registering block item '{}'", idstr);
                Items.registerBlock(interceptor.value);
            }
        }

        return false;
    }

    @Override
    public Path mod() {
        return mod;
    }
}
