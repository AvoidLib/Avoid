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
import pl.olafcio.reflectstorm.ReflectStorm;

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
        PreModLoader.post();

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
                        ReflectStorm.classpath().klassLoader(ReflectStorm.Classpath.BuiltinCL.APP)
                                                .addURI(out.toPath().toUri());

                        for (var config : configs.entrySet()) {
                            Mixins.addConfiguration(config.getValue().runtimeName);
                        }
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