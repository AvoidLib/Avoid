package re.avoidmc.modyaml;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;
import pl.olafcio.avoid.util.ModEnvironment;
import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.services.standard.IAvoidModDescriber;
import pl.olafcio.avoid_loader.interfaces.standard.IAvoidModMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModYamlDescriber implements IAvoidModDescriber {
    @Override
    public @Nullable IAvoidModMetadata describe(ModFile modFile) throws IOException {
        var stream = modFile.getResourceAsStream("avoid.mod.yml");
        if (stream != null)
            return make(stream);

        var stream2 = modFile.getResourceAsStream("avoid.mod.yaml");
        if (stream2 != null)
            return make(stream2);

        return null;
    }

    private static @NotNull IAvoidModMetadata make(InputStream stream) throws IOException {
        Map<String, Object> yml;

        try (stream) {
            yml = new Yaml().load(stream);
        }

        return new IAvoidModMetadata() {
            @Override
            public String versionSystem() {
                return (String) yml.get("version-system");
            }

            @Override
            public String description() {
                return (String) yml.get("description");
            }

            @Override
            public String mainClass() {
                return (String) yml.get("main-class");
            }

            @Override
            public ModEnvironment env() {
                return yml.containsKey("environment")
                        ? ModEnvironment.valueOf(((String) yml.get("environment")).toUpperCase())
                        : ModEnvironment.ALL;
            }

            @Override
            public List<JIJ> jars() {
                var output = new ArrayList<JIJ>();

                if (yml.containsKey("jars")) {
                    var jars = (List<?>) yml.get("jars");

                    for (var embeddedJAR : jars) {
                        String jarPath;

                        Boolean library;
                        Boolean mod;

                        if (embeddedJAR instanceof Map<?,?>) {
                            var obj = (Map<?,?>) embeddedJAR;

                            jarPath = (String) obj.get("path");

                            library = !obj.containsKey("library") ? null : (boolean) obj.get("library");
                            mod =     !obj.containsKey(  "mod"  ) ? null : (boolean) obj.get(  "mod"  );
                        } else {
                            jarPath = (String) embeddedJAR;

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
            public List<String> libraries() {
                return yml.containsKey("libraries")
                            ? ((List<?>) yml.get("libraries"))
                                            .stream()
                                            .map(obj -> (String) obj)
                                            .toList()
                            : List.of();
            }

            @Override
            public List<String> skipClasses() {
                return yml.containsKey("skip-classes")
                        ? ((List<?>) yml.get("skip-classes"))
                                        .stream()
                                        .map(obj -> (String) obj)
                                        .toList()
                        : List.of();
            }

            @Override
            public String id() {
                return (String) yml.get("id");
            }

            @Override
            public String name() {
                return (String) yml.get("name");
            }

            @Override
            public String version() {
                return String.valueOf( yml.get("version") );
            }

            @Override
            public String author() {
                return (String) yml.get("author");
            }

            @Override
            public String @NotNull [] mixins() {
                var mixins = yml.get("mixins");

                if (mixins == null) {
                    return IAvoidModMetadata.super.mixins();
                } else if (mixins instanceof List<?> array) {
                    var out = new String[array.size()];
                    var i = 0;

                    for (var el : array) {
                        out[i++] = (String) el;
                    }

                    return out;
                } else {
                    return new String[]{(String) mixins};
                }
            }

            @Override
            public Map<String, Object> map() {
                return yml;
            }
        };
    }
}
