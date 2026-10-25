package pl.olafcio.avoid_platform.loader_impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.services.IModScanner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public final class AvoidModVerifier implements IModScanner {
    private static final Logger LOGGER
                       = LoggerFactory.getLogger("AvoidLoader/Mod Verifier");

    @Override
    public void process(IMod mod, JarFile jar) throws IOException {
        if (mod instanceof AvoidModContainer avoid) {
            var signaturesFile = jar.getEntry("avoid.signatures");
            if (signaturesFile != null) {
                verifySignatures(avoid.path(), jar, signaturesFile);
            }
        }
    }

    private void verifySignatures(Path mod, JarFile jar, ZipEntry signaturesFile)
            throws IOException
    {
        final var signatures = new HashMap<String, String>();

        try (var stream = jar.getInputStream(signaturesFile)) {
            var lines = new String(stream.readAllBytes(), StandardCharsets.UTF_8)
                    .split("\n");

            for (var line : lines) {
                int slash = line.lastIndexOf('/');

                signatures.put(line.substring(0, slash), line.substring(slash + 1));
            }
        }

        var entries = jar.entries();

        while (entries.hasMoreElements()) {
            var entry = entries.nextElement();
            var signature = signatures.get(entry.getName());

            if (signature != null) {
                String sha1;

                try (var stream = jar.getInputStream(entry)) {
                    sha1 = DigestUtils.sha1Hex(stream);
                }

                if (!sha1.equals(signature)) {
                    LOGGER.error("""
                            
                            
                            
                            Mod '{}' has been tampered with!
                            Did you download from the original website (e.g. Modrinth)?
                            
                            [[ Fail Description ]]
                            
                            > File: '{}'
                            > Signature: '{}' (expected '{}')
                            
                            
                            """, mod.toAbsolutePath(), entry.getName(), sha1, signature);

                    throw new RuntimeException("Failed to verify Avoid mod signatures: %s".formatted(mod.toAbsolutePath()));
                }
            }
        }
    }
}
