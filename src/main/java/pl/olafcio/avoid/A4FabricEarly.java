package pl.olafcio.avoid;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;

// Idea taken from https://modrinth.com/mod/thatorthis by https://modrinth.com/user/EZForever

@ApiStatus.Internal
public final class A4FabricEarly implements LanguageAdapter {
    @Override
    public <T> T create(ModContainer mod, String value, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    static {
        var mods = FabricLoader.getInstance().getGameDir().resolve("mods");

        try (var files = Files.list(mods)) {
            files.forEach(f -> {
                if (f.getFileName().endsWith(".jar") && f.toFile().isFile()) {
                    var zip_properties = new HashMap<String, String>();
                    zip_properties.put("create", "false");

                    URI zip_disk = URI.create("jar:file:" + f.toAbsolutePath());

                    try (FileSystem zipfs = FileSystems.newFileSystem(zip_disk, zip_properties)) {
                        var avoid = zipfs.getPath("avoid.mod.json");
                        if (Files.isRegularFile(avoid)) {
                            var fabric = zipfs.getPath("fabric.mod.json");
                            if (Files.isRegularFile(fabric)) {
                                Files.delete(fabric);
                            }
                        }

                        Files.delete(avoid);
                    } catch (IOException e) {
                        //noinspection RedundantStringFormatCall
                        System.out.println("[Avoid / Early] [JarSuppresser] Failed to handle jar '%s'".formatted(f.toString()));
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("[Avoid / Early] Failed to iterate through mods");
        }
    }
}
