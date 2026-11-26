package pl.olafcio.avoid_platform.loader_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.PleaseAppendMCMeta;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.services.IModProcessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class MCMetaAppenderService implements IModProcessor {
    private static String packFormat;
    private static final Logger LOGGER = LoggerFactory.getLogger("AvoidLoader/MCMeta Appender");

    static {
        try (var stream = ClassLoader.getSystemResourceAsStream("version.json")) {
            packFormat = new String(stream.readAllBytes()).split("data_major\": ")[1]
                                                          .split(",")[0]
                                                          .trim();
        } catch (Exception e) {
            LOGGER.warn("Failed to retrieve required data major version; omitting");
        }
    }

    @Override
    public void process(IMod mod) {
        if (mod.getClass().isAnnotationPresent(PleaseAppendMCMeta.class) && !Files.isSymbolicLink(mod.getModFile().getPath())) {
            byte[] pack = """
                          {
                              "pack": {
                                  "description": "Avoid addon (generated pack.mcmeta)",
                                  "min_format": 1,
                                  "max_format": 1000,
                                  "supported_formats": [1, 1000]%s
                              }
                          }
                          """.formatted(packFormat == null ? "" : ",\n" +
                                  "\"pack_format\": " + packFormat
                          )
                          .getBytes(StandardCharsets.UTF_8);

            try {
                mod.getModFile().addResource("pack.mcmeta", pack);
            } catch (IOException e) {
                LOGGER.warn("Failed to append pack.mcmeta to mod: '{}'", mod.getModFile().getPath(), e);
            }
        }
    }
}
