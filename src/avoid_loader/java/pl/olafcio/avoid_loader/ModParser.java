package pl.olafcio.avoid_loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.util.ModEnvironment;
import pl.olafcio.avoid_platform.AvoidWrappedLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class ModParser {
    private static final Gson GSON
                   = new Gson();

    private final Path mod;
    private final Set<Path> loadedMods;
    private final ArrayList<PreModContainer> avoidMods;
    private final ModClassLoader classLoader;

    public ModParser(Path mod, Set<Path> loadedMods, ArrayList<PreModContainer> avoidMods, ModClassLoader classLoader) {
        this.mod = mod;
        this.loadedMods = loadedMods;
        this.avoidMods = avoidMods;
        this.classLoader = classLoader;
    }

    public ModParser(Path mod, Set<Path> loadedMods, ArrayList<PreModContainer> avoidMods) {
        try {
            this.mod = mod;
            this.loadedMods = loadedMods;
            this.avoidMods = avoidMods;
            this.classLoader = new ModClassLoader(new URL[]{ mod.toUri().toURL() }, this.getClass().getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to initialize mod class loader; path to URL conversion failed", e);
        }
    }

    private final Path tmp = pl.olafcio.avoid_platform.AvoidWrappedLoader.getGameDir().resolve(".cache/avoid_loader/.tmp");
    private static String packFormat;

    static {
        try (var stream = ClassLoader.getSystemResourceAsStream("version.json")) {
            packFormat = new String(stream.readAllBytes()).split("data_major\": ")[1]
                                                          .split(",")[0]
                                                          .trim();
        } catch (Exception e) {
            LoggerFactory.getLogger("Avoid/Mod Parser")
                         .warn("Failed to retrieve required data major version; omitting");
        }
    }

    private class MCMetaAppender {
        private FileInputStream fis;
        private ZipInputStream zis;

        private final Path packedJAR;

        private void closeStuff() {
            if (zis != null)
                try                         { zis.close(); }
                catch (IOException ignored) {}

            if (fis != null)
                try                         { fis.close(); }
                catch (IOException ignored) {}
        }

        MCMetaAppender() {
            try {
                fis = new FileInputStream(mod.toFile());
                zis = new ZipInputStream(fis);

                Files.createDirectories(tmp);
                packedJAR = tmp.resolve(mod.getFileName());

                if (addPackMCMeta()) {
                    closeStuff();
                    override();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Couldn't append pack.mcmeta to mod JAR: '%s'  (phase: begin)".formatted(mod.toAbsolutePath()), e);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't append pack.mcmeta to mod JAR: '%s'  (phase: end)".formatted(mod.toAbsolutePath()), e);
            } finally {
                closeStuff();
            }
        }

        private void override() throws IOException {
            Files.move(packedJAR, mod, StandardCopyOption.REPLACE_EXISTING);
        }

        private boolean addPackMCMeta() {
            FileOutputStream fos = null;
            ZipOutputStream zos = null;

            try {
                fos = new FileOutputStream(packedJAR.toFile());
                zos = new ZipOutputStream(fos);

                ZipEntry entry;

                while ((entry = zis.getNextEntry()) != null) {
                    var name = entry.getName();
                    while (name.startsWith("/"))
                        name = name.substring(1);

                    if (name.equals("pack.mcmeta"))
                        return false;

                    zos.putNextEntry(entry);
                    zis.transferTo(zos);
                }

                zos.putNextEntry(new ZipEntry("pack.mcmeta"));
                zos.write("""
                        {
                            "pack": {
                                "description": "Avoid addon (generated pack.mcmeta)",
                                "min_format": 1,
                                "max_format": 1000,
                                "supported_formats": [1, 1000]%s
                            }
                        }
                        """.formatted(packFormat == null ? "" : ",\n" +
                                                                "\"pack_format\": "  + packFormat)
                           .getBytes(StandardCharsets.UTF_8));

                zos.finish();
            } catch (IOException e) {
                throw new RuntimeException("Couldn't append pack.mcmeta to mod JAR: '%s'  (phase: process)".formatted(mod.toAbsolutePath()), e);
            } finally {
                if (zos != null)
                    try                       { zos.close(); }
                    catch (Exception ignored) {}

                if (fos != null)
                    try                       { fos.close(); }
                    catch (Exception ignored) {}
            }

            return true;
        }
    }

    @SuppressWarnings("SimplifiableConditionalExpression")
    public void load() {
        try (var jar = new JarFile(mod.toFile())) {
            var manifestFile = jar.getEntry("avoid.mod.json");
            if (manifestFile == null)
                return;

            var signaturesFile = jar.getEntry("avoid.signatures");
            if (signaturesFile != null) {
                verifySignatures(jar, signaturesFile);
            }

            byte[] manifestData = jar.getInputStream(manifestFile)
                                     .readAllBytes();

            var json = GSON.fromJson(new String(manifestData), JsonObject.class);
            if (json == null) {
                PreModLoader.LOGGER.error("Failed to read Avoid mod manifest: {}", mod.toAbsolutePath());
                return;
            }

            var manifest = new AvoidManifest(json);

            int schema = manifest.get("__schema").getAsInt();
            if (schema != 1) {
                PreModLoader.LOGGER.error("Invalid Avoid mod manifest __schema: {} [{}]", schema, mod.toAbsolutePath());
                return;
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
                    PreModLoader.LOGGER.warn("Skipping client-only mod: {} [{}]", name, id);
                    return;
                }
            } else if (env == ModEnvironment.SERVER) {
                if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.SERVER) {
                    PreModLoader.LOGGER.warn("Skipping server-only mod: {} [{}]", name, id);
                    return;
                }
            }

            avoidMods.add(new PreModContainer(mod, json, classLoader, null));

            if (manifest.has("jars")) {
                var jars = manifest.get("jars").getAsJsonArray();
                var folder = pl.olafcio.avoid_platform.AvoidWrappedLoader.getGameDir().resolve(".cache/avoid_loader/embedded_jars");

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
                            classLoader.addURL(path.toUri().toURL());

                            new ModParser(path, loadedMods, avoidMods, classLoader).load();
                        } else {
                            new ModParser(path, loadedMods, avoidMods).load();
                        }
                    } else {
                        if (!library)
                            throw new RuntimeException("Jar-in-jar entry with library:false & mod:false; cannot load into void");

                        classLoader.addURL(path.toUri().toURL());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while parsing JAR file '%s'".formatted(mod.toAbsolutePath()), e);
        }

        new MCMetaAppender();
    }

    private void verifySignatures(JarFile jar, ZipEntry signaturesFile)
            throws IOException
    {
        final var signatures = new HashMap<String, String>();

        try (var stream = jar.getInputStream(signaturesFile)) {
            var lines = new String(stream.readAllBytes(), StandardCharsets.UTF_8)
                                         .split("\n");

            for (var line : lines) {
                int slash = line.lastIndexOf('/');

                signatures.put(line.substring(0, slash), line.substring(slash + 1));
            }
        }

        var entries = jar.entries();

        while (entries.hasMoreElements()) {
            var entry = entries.nextElement();
            var signature = signatures.get(entry.getName());

            if (signature != null) {
                String sha1;

                try (var stream = jar.getInputStream(entry)) {
                    sha1 = DigestUtils.sha1Hex(stream);
                }

                if (!sha1.equals(signature)) {
                    PreModLoader.LOGGER.error("""
                            
                            
                            
                            Mod '{}' has been tampered with!
                            Did you download from the original website (e.g. Modrinth)?
                            
                            [[ Fail Description ]]
                            
                            > File: '{}'
                            > Signature: '{}' (expected '{}')
                            
                            
                            """, mod.toAbsolutePath(), entry.getName(), sha1, signature);

                    throw new RuntimeException("Failed to verify Avoid mod signatures: %s".formatted(mod.toAbsolutePath()));
                }
            }
        }
    }
}
