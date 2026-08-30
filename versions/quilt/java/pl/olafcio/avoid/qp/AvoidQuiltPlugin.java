package pl.olafcio.avoid.qp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.metadata.ModEnvironment;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.*;
import org.quiltmc.loader.api.gui.*;
import org.quiltmc.loader.api.plugin.*;
import org.quiltmc.loader.api.plugin.solver.ModLoadOption;
import org.quiltmc.loader.api.plugin.solver.QuiltFileHasher;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AvoidQuiltPlugin implements QuiltLoaderPlugin {
    private QuiltPluginContext context;
    private int mod = 1;

    @Override
    public void load(QuiltPluginContext context, Map<String, LoaderValue> previousData) {
        this.context = context;
    }

    @Override
    public ModLoadOption[] scanZip(Path path, Path root, ModLocation location, QuiltTreeNode guiNode) throws IOException {
        File file;

        try {
            file = path.toFile();
        } catch (java.lang.Exception e) {
            return null;
        }

        try (var zip = new ZipFile(file)) {
            ZipEntry entry;

            if ((entry = zip.getEntry("avoid.mod.json")) != null) {
                byte[] bytes;

                try (var stream = zip.getInputStream(entry)) {
                    bytes = stream.readAllBytes();
                }

                var data = new Gson().fromJson(new String(bytes, StandardCharsets.UTF_8), JsonObject.class);

                return new ModLoadOption[]{ new ModLoadOption() {
                    @Override
                    public QuiltPluginContext loader() {
                        return AvoidQuiltPlugin.this.context;
                    }

                    @Override
                    public ModMetadataExt metadata() {
                        return new ModMetadataExt() {
                            @Override
                            public @Nullable ModPlugin plugin() {
                                return null;
                            }

                            @Override
                            public Map<String, Collection<ModEntrypoint>> getEntrypoints() {
                                return Map.of();
                            }

                            @Override
                            public Map<String, String> languageAdapters() {
                                return Map.of();
                            }

                            @Override
                            public Collection<String> mixins(EnvType env) {
                                return List.of();
                            }

                            @Override
                            public Collection<String> accessWideners() {
                                return List.of();
                            }

                            @Override
                            public ModEnvironment environment() {
                                return ModEnvironment.UNIVERSAL;
                            }

                            @Override
                            public String id() {
                                return data.get("id").getAsString();
                            }

                            @Override
                            public String group() {
                                return "";
                            }

                            @Override
                            public Version version() {
                                return Version.of(data.get("version").getAsString());
                            }

                            @Override
                            public String name() {
                                return data.get("name").getAsString();
                            }

                            @Override
                            public String description() {
                                return data.get("description").getAsString();
                            }

                            @Override
                            public Collection<ModLicense> licenses() {
                                if (!(data.has("license")))
                                    return List.of();

                                return List.of(new ModLicense() {
                                    @Override
                                    public String name() {
                                        return data.get("license").getAsString();
                                    }

                                    @Override
                                    public String id() {
                                        return null;
                                    }

                                    @Override
                                    public String url() {
                                        return "";
                                    }

                                    @Override
                                    public String description() {
                                        return "";
                                    }
                                });
                            }

                            @Override
                            public Collection<ModContributor> contributors() {
                                return List.of(ModContributor.of(data.get("author").getAsString(), List.of()));
                            }

                            @Override
                            public @Nullable String getContactInfo(String key) {
                                return null;
                            }

                            @Override
                            public Map<String, String> contactInfo() {
                                return Map.of();
                            }

                            @Override
                            public Collection<ModDependency> depends() {
                                return List.of(ModDependency.Only.of("avoidlib"));
                            }

                            @Override
                            public Collection<ModDependency> breaks() {
                                return List.of();
                            }

                            @Override
                            public @Nullable String icon(int size) {
                                return null;
                            }

                            @Override
                            public boolean containsValue(String key) {
                                return false;
                            }

                            @Override
                            public @Nullable LoaderValue value(String key) {
                                return null;
                            }

                            @Override
                            public Map<String, LoaderValue> values() {
                                return Map.of();
                            }
                        };
                    }

                    @Override
                    public Path from() {
                        return path;
                    }

                    @Override
                    public Path resourceRoot() {
                        return path;
                    }

                    @Override
                    public boolean isMandatory() {
                        return true;
                    }

                    @Override
                    public @Nullable String namespaceMappingFrom() {
                        return "";
                    }

                    @Override
                    public boolean needsTransforming() {
                        return false;
                    }

                    @Override
                    public byte[] computeOriginHash(QuiltFileHasher hasher) throws IOException {
                        return new byte[0];
                    }

                    @Override
                    public QuiltLoaderIcon modFileIcon() {
                        return QuiltLoaderGui.iconJavaPackage();
                    }

                    @Override
                    public QuiltLoaderIcon modTypeIcon() {
                        return QuiltLoaderGui.iconPackage();
                    }

                    @Override
                    public ModContainerExt convertToMod(Path transformedResourceRoot) {
                        var mlo = this;
                        var sourcePaths = loader().manager().convertToSourcePaths(path);

                        return new ModContainerExt() {
                            @Override
                            public ModMetadataExt metadata() {
                                return mlo.metadata();
                            }

                            @Override
                            public String pluginId() {
                                return context.pluginId();
                            }

                            @Override
                            public String modType() {
                                return "Avoid";
                            }

                            @Override
                            public boolean shouldAddToQuiltClasspath() {
                                return false;
                            }

                            @Override
                            public Path rootPath() {
                                return path;
                            }

                            @Override
                            public List<List<Path>> getSourcePaths() {
                                return sourcePaths;
                            }

                            @Override
                            public BasicSourceType getSourceType() {
                                return ModContainer.BasicSourceType.OTHER;
                            }
                        };
                    }

                    @Override
                    public String shortString() {
                        return "AvoidLib";
                    }

                    @Override
                    public String getSpecificInfo() {
                        return "disk";
                    }

                    @Override
                    public QuiltLoaderText describe() {
                        return QuiltLoaderText.of(data.get("name").getAsString());
                    }
                }};
            }
        }

        return QuiltLoaderPlugin.super.scanZip(path, root, location, guiNode);
    }

    @Override
    public void unload(Map<String, LoaderValue> data) {}
}
