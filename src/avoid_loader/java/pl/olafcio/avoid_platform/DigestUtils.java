package pl.olafcio.avoid_platform;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public enum DigestUtils {
    ;

    private static final MessageDigest sha1;
    static {
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize DigestUtils", e);
        }
    }

    public static byte[] sha1(byte[] convertme) {
        return sha1.digest(convertme);
    }

    public static byte[] sha1(InputStream stream) throws IOException {
        byte[] array = new byte[1024];

        while (true) {
            var length = stream.read(array, 0, 1024);
            if (length == -1)
                break;

            sha1.update(array, 0, length);
        }

        return sha1.digest();
    }

    public static String sha1Hex(byte[] convertme) throws IOException {
        return byteArray2Hex(sha1(convertme));
    }

    public static String sha1Hex(InputStream stream) throws IOException {
        return byteArray2Hex(sha1(stream));
    }

    private static String byteArray2Hex(final byte[] hash) {
        return HexFormat.of().formatHex(hash);
    }
}
