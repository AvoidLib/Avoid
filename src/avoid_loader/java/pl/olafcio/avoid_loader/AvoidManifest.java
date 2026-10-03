package pl.olafcio.avoid_loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class AvoidManifest {
    private final JsonObject object;

    AvoidManifest(JsonObject object) {
        this.object = object;
    }

    static class ManifestElement {
        private final String name;
        private final JsonElement element;

        private ManifestElement(String name, JsonElement element) {
            this.name = name;
            this.element = element;
        }

        public int getAsInt() {
            try {
                return element.getAsInt();
            } catch (Exception e) {
                throw new RuntimeException("'avoid.mod.json' has invalid field: '%s' (unparsable value)[expected int]".formatted(name), e);
            }
        }

        public String getAsString() {
            try {
                return element.getAsString();
            } catch (Exception e) {
                throw new RuntimeException("'avoid.mod.json' has invalid field: '%s' (unparsable value)[expected string]".formatted(name), e);
            }
        }

        public JsonArray getAsJsonArray() {
            try {
                return element.getAsJsonArray();
            } catch (Exception e) {
                throw new RuntimeException("'avoid.mod.json' has invalid field: '%s' (unparsable value)[expected array]".formatted(name), e);
            }
        }
    }

    @NotNull
    ManifestElement get(String name) {
        return new ManifestElement(name, Objects.requireNonNull(object.get(name), "'avoid.mod.json' has missing field: '%s'".formatted(name)));
    }

    boolean has(String name) {
        return object.has(name);
    }
}
