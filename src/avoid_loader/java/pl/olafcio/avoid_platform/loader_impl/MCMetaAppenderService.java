package pl.olafcio.avoid_platform.loader_impl;

import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.services.IModProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class MCMetaAppenderService implements IModProcessor {
    private final Path tmp = Path.of(".cache/avoid_loader/.tmp");
    private static String packFormat;

    static {
        try (var stream = ClassLoader.getSystemResourceAsStream("version.json")) {
            packFormat = new String(stream.readAllBytes()).split("data_major\": ")[1]
                                                          .split(",")[0]
                                                          .trim();
        } catch (Exception e) {
            LoggerFactory.getLogger("AvoidLoader/MCMeta Appender")
                         .warn("Failed to retrieve required data major version; omitting");
        }
    }

    @Override
    public void process(IMod mod) {
        if (mod instanceof AvoidModContainer avoid && !Files.isSymbolicLink(avoid.path()))
            new MCMetaAppender(mod.getModFile().getPath());
    }

    private class MCMetaAppender {
        private FileInputStream fis;
        private ZipInputStream zis;

        private final Path mod;
        private final Path packedJAR;

        MCMetaAppender(Path mod) {
            this.mod = mod;

            try {
                fis = new FileInputStream(mod.toFile());
                zis = new ZipInputStream(fis);

                Files.createDirectories(tmp);
                packedJAR = tmp.resolve(mod.getFileName());

                if (addPackMCMeta()) {
                    closeStuff();
                    override();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Couldn't append pack.mcmeta to mod JAR: '%s'  (phase: begin)".formatted(mod.toAbsolutePath()), e);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't append pack.mcmeta to mod JAR: '%s'  (phase: end)".formatted(mod.toAbsolutePath()), e);
            } finally {
                closeStuff();
            }
        }

        private void closeStuff() {
            if (zis != null)
                try                         { zis.close(); }
                catch (IOException ignored) {}

            if (fis != null)
                try                         { fis.close(); }
                catch (IOException ignored) {}
        }

        private void override() throws IOException {
            Files.move(packedJAR, mod, StandardCopyOption.REPLACE_EXISTING);
        }

        private boolean addPackMCMeta() {
            FileOutputStream fos = null;
            ZipOutputStream zos = null;

            try {
                fos = new FileOutputStream(packedJAR.toFile());
                zos = new ZipOutputStream(fos);

                ZipEntry entry;

                while ((entry = zis.getNextEntry()) != null) {
                    var name = entry.getName();
                    while (name.startsWith("/"))
                        name = name.substring(1);

                    if (name.equals("pack.mcmeta"))
                        return false;

                    zos.putNextEntry(entry);
                    zis.transferTo(zos);
                }

                zos.putNextEntry(new ZipEntry("pack.mcmeta"));
                zos.write("""
                            {
                                "pack": {
                                    "description": "Avoid addon (generated pack.mcmeta)",
                                    "min_format": 1,
                                    "max_format": 1000,
                                    "supported_formats": [1, 1000]%s
                                }
                            }
                            """.formatted(packFormat == null ? "" : ",\n" +
                                "\"pack_format\": "  + packFormat)
                        .getBytes(StandardCharsets.UTF_8));

                zos.finish();
            } catch (IOException e) {
                throw new RuntimeException("Couldn't append pack.mcmeta to mod JAR: '%s'  (phase: process)".formatted(mod.toAbsolutePath()), e);
            } finally {
                if (zos != null)
                    try                       { zos.close(); }
                    catch (Exception ignored) {}

                if (fos != null)
                    try                       { fos.close(); }
                    catch (Exception ignored) {}
            }

            return true;
        }
    }
}
