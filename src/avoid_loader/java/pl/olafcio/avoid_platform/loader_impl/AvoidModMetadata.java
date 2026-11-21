package pl.olafcio.avoid_platform.loader_impl;

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.util.ModEnvironment;
import pl.olafcio.avoid_loader.interfaces.standard.IAvoidModMetadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AvoidModMetadata implements IAvoidModMetadata {
    private final JsonObject manifest;

    public AvoidModMetadata(JsonObject manifest) {
        this.manifest = manifest;
    }

    @Override
    public String versionSystem() {
        return manifest.get("version-system").getAsString();
    }

    @Override
    public String description() {
        return manifest.get("description").getAsString();
    }

    @Override
    public String mainClass() {
        return manifest.get("main-class").getAsString();
    }

    @Override
    public ModEnvironment env() {
        return manifest.has("environment")
                ? ModEnvironment.valueOf(manifest.get("environment").getAsString().toUpperCase())
                : ModEnvironment.ALL;
    }

    @Override
    public List<String> libraries() {
        return manifest.has("libraries")
                ? manifest.getAsJsonArray("libraries")
                          .asList()
                          .stream()
                          .map(JsonElement::getAsString)
                          .toList()
                : List.of();
    }

    @Override
    public List<String> skipClasses() {
        return manifest.has("skip-classes")
                ? manifest.get("skip-classes").getAsJsonArray().asList().stream()
                                                                        .map(JsonElement::getAsString)
                                                                        .toList()
                : List.of();
    }

    @Override
    public List<JIJ> jars() {
        var output = new ArrayList<JIJ>();

        if (manifest.has("jars")) {
            var jars = manifest.get("jars").getAsJsonArray();

            for (var embeddedJAR : jars) {
                String jarPath;

                Boolean library;
                Boolean mod;

                if (embeddedJAR.isJsonObject()) {
                    var obj = (JsonObject) embeddedJAR;

                    jarPath = obj.get("path").getAsString();

                    library = !obj.has("library") ? null : obj.get("library").getAsBoolean();
                    mod =     !obj.has("mod"    ) ? null : obj.get(  "mod"  ).getAsBoolean();
                } else {
                    jarPath = embeddedJAR.getAsString();

                    library = null;
                    mod = null;
                }

                output.add(new JIJ() {
                    @Override
                    public String path() {
                        return jarPath;
                    }

                    @Override
                    public Boolean library() {
                        return library;
                    }

                    @Override
                    public Boolean mod() {
                        return mod;
                    }
                });
            }
        }

        return output;
    }

    @Override
    public String id() {
        return manifest.get("id").getAsString();
    }

    @Override
    public String name() {
        return manifest.get("name").getAsString();
    }

    @Override
    public String version() {
        return manifest.get("version").getAsString();
    }

    @Override
    public String author() {
        return manifest.get("author").getAsString();
    }

    @Override
    public String @NotNull [] mixins() {
        var mixins = manifest.get("mixins");

        if (mixins == null) {
            return IAvoidModMetadata.super.mixins();
        } else if (mixins instanceof JsonArray array) {
            var out = new String[array.size()];
            var i = 0;

            for (var el : array) {
                out[i++] = el.getAsString();
            }

            return out;
        } else {
            return new String[]{mixins.getAsString()};
        }
    }

    @Override
    public Map<String, Object> map() {
        return Convert.convertMap(manifest);
    }

    private enum Convert {
        ;

        private static Object convert(JsonElement json) {
            return switch (json) {
                case JsonObject manifest -> convertMap(manifest);
                case JsonArray array -> array.asList().stream().map(Convert::convert).toList();
                case JsonNull _jsonNull -> null;
                case JsonPrimitive primitive -> primitive.isString() ? primitive.getAsString() :
                                                primitive.isNumber() ? primitive.getAsNumber() :
                                                primitive.isBoolean() ? primitive.getAsBoolean() :
                                                null;

                case null, default -> throw new RuntimeException("Unsure how to convert '%s'".formatted(json));
            };
        }

        private static @NotNull Map<String, Object> convertMap(JsonObject manifest) {
            var map = new HashMap<String, Object>(manifest.asMap());

            map.replaceAll((k, v) -> convert((JsonElement) v));

            return map;
        }
    }
}
