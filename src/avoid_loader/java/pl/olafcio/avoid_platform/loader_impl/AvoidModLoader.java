package pl.olafcio.avoid_platform.loader_impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.util.ModEnvironment;
import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.IModFile;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_platform.AvoidWrappedLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public final class AvoidModLoader implements IModLoader {
    private static final Gson GSON
                   = new Gson();

    private static final Logger LOGGER
                       = LoggerFactory.getLogger("AvoidLoader/Mod Loader");

    @Override
    public Collection<IMod> process(IModFile iModFile) {
        if (iModFile instanceof ModFile modFile) {
            Path mod = modFile.getPath();
            AvoidModContainer container;

            var jar = modFile.getJar();

            try {
                var manifestFile = jar.getEntry("avoid.mod.json");
                if (manifestFile == null)
                    return List.of();

                byte[] manifestData = jar.getInputStream(manifestFile)
                                         .readAllBytes();

                var json = GSON.fromJson(Arrays.stream(new String(manifestData).split("\n"))
                                                                               .filter(line -> !line.stripLeading().startsWith("//"))
                                                                               .collect(Collectors.joining("\n")), JsonObject.class);
                if (json == null) {
                    LOGGER.error("Failed to read Avoid mod manifest: {}", mod.toAbsolutePath());
                    return null;
                }

                var manifest = new AvoidManifest(json);

                int schema = manifest.get("__schema").getAsInt();
                if (schema != 1) {
                    LOGGER.error("Invalid Avoid mod manifest __schema: {} [{}]", schema, mod.toAbsolutePath());
                    return null;
                }

                String id = manifest.get("id").getAsString();
                String version = manifest.get("version").getAsString();
                String versionSystem = manifest.get("version-system").getAsString();

                String name = manifest.get("name").getAsString();
                String author = manifest.get("author").getAsString();
                String description = manifest.get("description").getAsString();

                ModEnvironment env = manifest.has("environment")
                        ? ModEnvironment.valueOf(manifest.get("environment").getAsString().toUpperCase())
                        : ModEnvironment.ALL;

                if (env == ModEnvironment.CLIENT) {
                    if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT) {
                        LOGGER.warn("Skipping client-only mod: {} [{}]", name, id);
                        return null;
                    }
                } else if (env == ModEnvironment.SERVER) {
                    if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.SERVER) {
                        LOGGER.warn("Skipping server-only mod: {} [{}]", name, id);
                        return null;
                    }
                }

                container = new AvoidModContainer(modFile, this, json, iModFile instanceof JIJModFile jij ? jij.parent : null);
            } catch (IOException e) {
                throw new RuntimeException("Error while parsing JAR file '%s'".formatted(mod.toAbsolutePath()), e);
            }

            try {
                extractJIJ(container);
            } catch (IOException e) {
                throw new RuntimeException("Error while resolving JIJ of JAR file '%s'".formatted(mod.toAbsolutePath()), e);
            }

            return List.of(container);
        }

        return List.of();
    }

    private void extractJIJ(AvoidModContainer container) throws IOException {
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

                var jar = container.getModFile().getJar();
                var entry = jar.getJarEntry(jarPath);

                try (var stream = jar.getInputStream(entry)) {
                    path = folder.resolve(DigestUtils.sha1Hex(stream) + ".jar");
                }

                try (var fos = new FileOutputStream(path.toFile())) {
                    try (var stream = jar.getInputStream(entry)) {
                        stream.transferTo(fos);
                    }
                }

                if (mod) {
                    if (library) {
                        recall(new JIJModFile(path, container.classLoader(), container));

                        container.classLoader().addURL(path.toUri().toURL());
                    } else {
                        recall(new JIJModFile(path, container));
                    }
                } else {
                    if (!library)
                        throw new RuntimeException("Jar-in-jar entry with library:false & mod:false; cannot load into void");

                    container.classLoader().addURL(path.toUri().toURL());
                }
            }
        }
    }
}
