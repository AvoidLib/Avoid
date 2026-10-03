package pl.olafcio.avoid_bundled;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.neoforged.fml.loading.FMLLoader;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixins;
import pl.olafcio.avoid_platform.AvoidWrappedLoader;
import pl.olafcio.avoid_impl.Reflect;
import pl.olafcio.avoid_loader.PreModContainer;
import pl.olafcio.avoid_loader.PreModLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
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
                                    stream.transferTo(zos);
                                }
                            }
                        }

                        zos.close();
                        bos.close();
                    }

                    var ucl = (URLClassLoader) this.getClass().getClassLoader();

                    Reflect.call(URLClassLoader.class, "addURL", ucl, new Class<?>[]{ URL.class }, new Object[]{
                            out.toURI().toURL()
                    });

                    for (var config : configs.values())
                        Mixins.addConfiguration(config.runtimeName);
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
