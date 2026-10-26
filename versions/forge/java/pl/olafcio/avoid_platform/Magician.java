package pl.olafcio.avoid_platform;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.ModuleLayerHandler;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher;
import org.spongepowered.asm.util.ReEntranceLock;
import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.PreModLoader;
import pl.olafcio.avoid_loader.interfaces.IMod;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Magician {

    public static final Logger LOGGER = LoggerFactory.getLogger("Avoid/PreLaunch");

    record MixinConfig(byte[] data, String runtimeName, String _package) {}

    private void loadMixinConfig(HashMap<String, MixinConfig> configs, String config, ZipFile zip, IMod mod) {
        byte[] data;
        JsonObject json;

        try (var stream = zip.getInputStream(zip.getEntry(config))) {
            data = stream.readAllBytes();
            json = new Gson().fromJson(new String(data, StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load mixin config '%s' from Avoid mod: '%s'".formatted(config, mod.metadata().id()), e);
        }

        configs.put(config, new MixinConfig(
                data,
                mod.metadata().id() + "-" + config,
                json.get("package").getAsString()
        ));
    }

    public void onPreLaunch() {
        PreModLoader.preload();

        var cache = Path.of(".").resolve(".cache/avoid_loader/exported_jars");

        try                   { Files.createDirectories(cache);                                                          }
        catch (IOException e) { throw new RuntimeException("Failed to create Avoid transformed mod cache directory", e); }

        PreModLoader.getMods().forEach(imod -> {
            if (imod instanceof AvoidModContainer mod && mod.metadata().mixins().length > 0) {
                try (var zip = new ZipFile(mod.getModFile().getPath().toFile())) {
                    var mixins = mod.metadata().mixins();
                    var configs = new HashMap<String, MixinConfig>();

                    for (var el : mixins) {
                        loadMixinConfig(configs, el, zip, mod);
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
//                        var service = (MixinServiceModLauncher) MixinService.getService();
//                        var mixinservice = Reflect.get(MixinService.class, "instance", MixinService.class);
//
//                        Reflect.set(MixinService.class, "service", mixinservice, new IMixinService() {
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
//                                    var delegate = Reflect.get(value.getClass(), "bytecodeProvider", IClassBytecodeProvider.class, value);
//
//                                    Reflect.set(value.getClass(), "bytecodeProvider", value, new IClassBytecodeProvider() {
//                                        @Override
//                                        public ClassNode getClassNode(String className, boolean b, int i) throws ClassNotFoundException, IOException {
//                                            if (classes.containsKey(className + ".class")) {
//                                                var data = classes.get(className + ".class");
//
//                                                var node = new ClassNode();
//                                                var reader = new ClassReader(data);
//
//                                                reader.accept(node, 0);
//
//                                                return node;
//                                            }
//
//                                            return delegate.getClassNode(className, b, i);
//                                        }
//
//                                        @Override
//                                        public ClassNode getClassNode(String className, boolean b) throws ClassNotFoundException, IOException {
//                                            if (classes.containsKey(className + ".class")) {
//                                                var data = classes.get(className + ".class");
//
//                                                var node = new ClassNode();
//                                                var reader = new ClassReader(data);
//
//                                                reader.accept(node, 0);
//
//                                                return node;
//                                            }
//
//                                            return delegate.getClassNode(className, b);
//                                        }
//
//                                        @Override
//                                        public ClassNode getClassNode(String className) throws ClassNotFoundException, IOException {
//                                            if (classes.containsKey(className + ".class")) {
//                                                var data = classes.get(className + ".class");
//
//                                                var node = new ClassNode();
//                                                var reader = new ClassReader(data);
//
//                                                reader.accept(node, 0);
//
//                                                return node;
//                                            }
//
//                                            return delegate.getClassNode(className);
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
//                                var config = configs.values().stream().filter(cfg -> cfg.runtimeName.equals(name)).findAny().orElse(null);
//                                if (config != null) {
//                                    return new ByteArrayInputStream(config.data);
//                                }
//
//                                var klass = classes.get(name.replace("/", "."));
//                                if (klass != null) {
//                                    return new ByteArrayInputStream(klass);
//                                }
//
//                                return service.getResourceAsStream(name);
//                            }
//
//                            boolean injected = false;
//                        });

                        PMLImpl.addToClasspath(out.toPath());

//                        var thread = new Thread(() -> {
//                            for (var config : configs.entrySet()) {
////                              try (var stream = zip.getInputStream(zip.getEntry(config.getKey()))) {
////                                  service.addMixinConfigContent(config.getValue().runtimeName, stream.readAllBytes());
////                              }
//
//                                Mixins.addConfiguration(config.getValue().runtimeName);
//                            }
//                        }, "Avoid-MixinLoading");
//
//                        var cl = Thread.currentThread().getContextClassLoader();
//
//                        thread.setContextClassLoader(new ClassLoader(cl) {
//                            @Override
//                            public String getName() {
//                                return super.getName();
//                            }
//
//                            @Override
//                            public Class<?> loadClass(String name) throws ClassNotFoundException {
//                                return cl.loadClass(name);
//                            }
//
//                            @Override
//                            public @Nullable InputStream getResourceAsStream(String name) {
//                                var config = configs.values().stream().filter(cfg -> cfg.runtimeName.equals(name)).findAny().orElse(null);
//                                if (config != null) {
//                                    return new ByteArrayInputStream(config.data);
//                                }
//
//                                var klass = classes.get(name.replace("/", "."));
//                                if (klass != null) {
//                                    return new ByteArrayInputStream(klass);
//                                }
//
//                                return super.getResourceAsStream(name);
//                            }
//                        });
//
//                        thread.start();
//                        thread.join();
//
//                        Reflect.set(MixinService.class, "service", mixinservice, service);

                        LOGGER.warn("Mixins are not implemented for Avoid addons on Forge!" + "\n" +
                                    "The necessary elements should be available on the game classpath, although not passed through mixin!");
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