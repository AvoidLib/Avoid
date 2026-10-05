package pl.olafcio.avoid_platform;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.util.ReEntranceLock;
import pl.olafcio.avoid_platform.AvoidWrappedLoader;
import pl.olafcio.avoid_platform.Reflect;
import pl.olafcio.avoid_loader.PreModContainer;
import pl.olafcio.avoid_loader.PreModLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Magician {
    record MixinConfig(String runtimeName, String _package) {}

    private void loadMixinConfig(HashMap<String, MixinConfig> configs, String config, ZipFile zip, PreModContainer mod) {
        JsonObject json;

        try (var stream = zip.getInputStream(zip.getEntry(config))) {
            json = new Gson().fromJson(new String(stream.readAllBytes(), StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load mixin config '%s' from Avoid mod: '%s'".formatted(config, mod.manifest().get("id").getAsString()), e);
        }

        configs.put(config, new MixinConfig(
                mod.manifest().get("id").getAsString() + "-" + config,
                json.get("package").getAsString()
        ));
    }

    public void onPreLaunch() {
        PreModLoader.preload();

        var cache = AvoidWrappedLoader.getGameDir().resolve(".cache/avoid_loader/exported_jars");

        try                   { Files.createDirectories(cache);                                                          }
        catch (IOException e) { throw new RuntimeException("Failed to create Avoid transformed mod cache directory", e); }

        PreModLoader.getMods().forEach(mod -> {
            if (mod.manifest().has("mixins")) {
                try (var zip = new ZipFile(mod.path().toFile())) {
                    var mixins = mod.manifest().get("mixins");
                    var configs = new HashMap<String, MixinConfig>();

                    if (mixins.isJsonArray()) {
                        for (var el : (JsonArray) mixins) {
                            loadMixinConfig(configs, el.getAsString(), zip, mod);
                        }
                    } else {
                        loadMixinConfig(configs, mixins.getAsString(), zip, mod);
                    }

                    var packages = configs.values().stream().map(MixinConfig::_package).map(pkg -> pkg.replace(".", "/") + "/").toList();
                    mod.packages = packages;

                    var classes = new HashMap<String, byte[]>();
                    var out = cache.resolve(DigestUtils.sha1Hex(new Gson().toJson(mod.manifest())) + ".jar").toFile();
                    if (!out.exists()) {
                        var bos = new FileOutputStream(out);
                        var zos = new ZipOutputStream(bos);

                        var entries = zip.entries();

                        while (entries.hasMoreElements()) {
                            var entry = entries.nextElement();
                            var name = entry.getName();

                            if (configs.containsKey(name)) {
                                zos.putNextEntry(new ZipEntry(configs.get(name).runtimeName));

                                try (var stream = zip.getInputStream(entry)) {
                                    stream.transferTo(zos);
                                }
                            } else if (name.endsWith(".class")) {
                                if (packages.stream().anyMatch(name::startsWith));
                                else {
                                    var node = new ClassNode();

                                    try (var stream = zip.getInputStream(entry)) {
                                        new ClassReader(stream).accept(node, 0);
                                    }

                                    if (node.invisibleAnnotations == null || node.invisibleAnnotations.stream().noneMatch(annotation -> annotation.desc.equals("Lpl/olafcio/avoid_loader/Expose;")))
                                        continue;
                                }

                                zos.putNextEntry(entry);

                                try (var stream = zip.getInputStream(entry)) {
                                    byte[] data = stream.readAllBytes();

                                    zos.write(data);
                                    classes.put(name.replace("/", "."), data);
                                }
                            }
                        }

                        zos.close();
                        bos.close();
                    } else {
                        var entries = zip.entries();

                        while (entries.hasMoreElements()) {
                            var entry = entries.nextElement();
                            var name = entry.getName();

                            if (name.endsWith(".class")) {
                                if (packages.stream().anyMatch(name::startsWith));
                                else {
                                    var node = new ClassNode();

                                    try (var stream = zip.getInputStream(entry)) {
                                        new ClassReader(stream).accept(node, 0);
                                    }

                                    if (node.invisibleAnnotations == null || node.invisibleAnnotations.stream().noneMatch(annotation -> annotation.desc.equals("Lpl/olafcio/avoid_loader/Expose;")))
                                        continue;
                                }

                                try (var stream = zip.getInputStream(entry)) {
                                    byte[] data = stream.readAllBytes();

                                    classes.put(name.replace("/", "."), data);
                                }
                            }
                        }
                    }

                    if (!configs.isEmpty()) {
//                        var service = (FMLMixinService) MixinService.getService();
//                        var mixinservice = Reflect.get(MixinService.class, "instance", MixinService.class);
//
//                        Reflect.set(MixinService.class, "service", mixinservice, new FMLMixinService() {
//                            @Override
//                            public void prepare() {
//                                service.prepare();
//                            }
//
//                            @Override
//                            public MixinEnvironment.Phase getInitialPhase() {
//                                return service.getInitialPhase();
//                            }
//
//                            @Override
//                            public void init() {
//                                service.init();
//                            }
//
//                            @Override
//                            public void beginPhase() {
//                                service.beginPhase();
//                            }
//
//                            @Override
//                            public void checkEnv(Object bootSource) {
//                                service.checkEnv(bootSource);
//                            }
//
//                            @Override
//                            public ReEntranceLock getReEntranceLock() {
//                                return service.getReEntranceLock();
//                            }
//
//                            @Override
//                            public String getSideName() {
//                                return service.getSideName();
//                            }
//
//                            @Override
//                            public void setBytecodeProvider(@Nullable IClassBytecodeProvider bytecodeProvider) {
//                                service.setBytecodeProvider(bytecodeProvider);
//                            }
//
//                            @Override
//                            public void offer(IMixinInternal internal) {
//                                service.offer(internal);
//                            }
//
//                            @Override
//                            public String getName() {
//                                return service.getName();
//                            }
//
//                            @Override
//                            public MixinEnvironment.CompatibilityLevel getMinCompatibilityLevel() {
//                                return service.getMinCompatibilityLevel();
//                            }
//
//                            @Override
//                            public MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel() {
//                                return service.getMaxCompatibilityLevel();
//                            }
//
//                            @Override
//                            public ILogger getLogger(String name) {
//                                return service.getLogger(name);
//                            }
//
//                            @Override
//                            public boolean isValid() {
//                                return service.isValid();
//                            }
//
//                            @Override
//                            public IClassProvider getClassProvider() {
//                                return service.getClassProvider();
//                            }
//
//                            @Override
//                            public IClassBytecodeProvider getBytecodeProvider() {
//                                var value = service.getBytecodeProvider();
//
//                                if (!injected) {
//                                    injected = true;
//
//                                    var delegate = Reflect.get(value.getClass(), "bytecodeProvider", BytecodeProvider.class, value);
//
//                                    Reflect.set(value.getClass(), "bytecodeProvider", value, new BytecodeProvider() {
//                                        @Override
//                                        public byte[] getByteCode(String className) throws ClassNotFoundException {
//                                            if (classes.containsKey(className + ".class")) {
//                                                return classes.get(className + ".class");
//                                            }
//
//                                            return delegate.getByteCode(className);
//                                        }
//                                    });
//                                }
//
//                                return value;
//                            }
//
//                            @Override
//                            public ITransformerProvider getTransformerProvider() {
//                                return service.getTransformerProvider();
//                            }
//
//                            @Override
//                            public IClassTracker getClassTracker() {
//                                return service.getClassTracker();
//                            }
//
//                            @Override
//                            public IMixinAuditTrail getAuditTrail() {
//                                return service.getAuditTrail();
//                            }
//
//                            @Override
//                            public IFeatureValidator getFeatureValidator() {
//                                return service.getFeatureValidator();
//                            }
//
//                            @Override
//                            public IAdviceProvider getAdviceProvider() {
//                                return service.getAdviceProvider();
//                            }
//
//                            @Override
//                            public IMixinTransformer getMixinTransformer() {
//                                return service.getMixinTransformer();
//                            }
//
//                            @Override
//                            public Collection<String> getPlatformAgents() {
//                                return service.getPlatformAgents();
//                            }
//
//                            @Override
//                            public IContainerHandle getPrimaryContainer() {
//                                return service.getPrimaryContainer();
//                            }
//
//                            @Override
//                            public Collection<IContainerHandle> getMixinContainers() {
//                                return service.getMixinContainers();
//                            }
//
//                            @Override
//                            public InputStream getResourceAsStream(String name) {
//                                return service.getResourceAsStream(name);
//                            }
//
//                            @Override
//                            public void addMixinConfigContent(String config, byte[] resource) {
//                                service.addMixinConfigContent(config, resource);
//                            }
//
//                            @Override
//                            public void addMixinContainer(IContainerHandle handle) {
//                                service.addMixinContainer(handle);
//                            }
//
//                            @Override
//                            public void clearMixinContainers() {
//                                service.clearMixinContainers();
//                            }
//
//                            boolean injected = false;
//                        });
//
//                        for (var config : configs.entrySet()) {
//                            try (var stream = zip.getInputStream(zip.getEntry(config.getKey()))) {
//                                service.addMixinConfigContent(config.getValue().runtimeName, stream.readAllBytes());
//                            }
//
//                            Mixins.addConfiguration(config.getValue().runtimeName);
//                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to process Avoid mod: '%s'".formatted(mod.manifest().get("id").getAsString()), e);
                }
            }
        });

//        Reflect.call(Mixins.class, "registerConfiguration", null, new Class<?>[]{ Config.class }, new Object[]{
//
//        });
    }
}
