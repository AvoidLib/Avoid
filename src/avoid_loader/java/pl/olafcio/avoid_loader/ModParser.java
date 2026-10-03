package pl.olafcio.avoid_loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.mods.ModEnvironment;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

class ModParser {
    private static final Gson GSON
                   = new Gson();

    private final Path mod;
    private final Set<Path> loadedMods;
    private final ArrayList<PreModContainer> avoidMods;

    public ModParser(Path mod, Set<Path> loadedMods, ArrayList<PreModContainer> avoidMods) {
        this.mod = mod;
        this.loadedMods = loadedMods;
        this.avoidMods = avoidMods;
    }

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

            avoidMods.add(new PreModContainer(mod, json, new ModClassLoader(new URL[]{ mod.toUri().toURL() }, this.getClass().getClassLoader())));
        } catch (IOException e) {
            throw new RuntimeException("Error while parsing JAR file '%s'".formatted(mod.toAbsolutePath()), e);
        }
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
