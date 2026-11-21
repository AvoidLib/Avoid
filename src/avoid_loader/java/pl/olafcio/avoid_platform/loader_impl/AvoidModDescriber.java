package pl.olafcio.avoid_platform.loader_impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.services.standard.IAvoidModDescriber;
import pl.olafcio.avoid_loader.interfaces.standard.IAvoidModMetadata;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class AvoidModDescriber implements IAvoidModDescriber {
    private static final Gson GSON
                   = new Gson();

    private static final Logger LOGGER
                       = LoggerFactory.getLogger("AvoidLoader/Mod Describer");

    @Override
    public IAvoidModMetadata describe(ModFile modFile) throws IOException {
        var manifestFile = modFile.getResourceAsStream("avoid.mod.json");
        if (manifestFile == null)
            return null;

        final byte[] manifestData;
        try {
            manifestData = manifestFile.readAllBytes();
        } finally {
            manifestFile.close();
        }

        var json = GSON.fromJson(Arrays.stream(new String(manifestData).split("\n"))
                                       .filter(line -> !line.stripLeading().startsWith("//"))
                                       .collect(Collectors.joining("\n")), JsonObject.class);
        if (json == null) {
            LOGGER.error("Failed to read Avoid mod manifest: {}", modFile.getPath().toAbsolutePath());
            return null;
        }

        var manifest = new AvoidManifest(json);

        int schema = manifest.get("__schema").getAsInt();
        if (schema != 1) {
            LOGGER.error("Invalid Avoid mod manifest __schema: {} [{}]", schema, modFile.getPath().toAbsolutePath());
            return null;
        }

        assertPropertiesExist(manifest);

        return makeMetadata(json);
    }

    private static @NotNull IAvoidModMetadata makeMetadata(JsonObject manifest) {
        return new AvoidModMetadata(manifest);
    }

    @SuppressWarnings("unused")
    private static void assertPropertiesExist(AvoidManifest manifest) {
        String id = manifest.get("id").getAsString();
        String version = manifest.get("version").getAsString();
        String versionSystem = manifest.get("version-system").getAsString();

        String name = manifest.get("name").getAsString();
        String author = manifest.get("author").getAsString();
        String description = manifest.get("description").getAsString();
    }
}
