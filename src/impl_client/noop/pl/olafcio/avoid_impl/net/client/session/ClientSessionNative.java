package pl.olafcio.avoid_impl.net.client.session;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public enum ClientSessionNative {
    ;

    public static String getNick() {
        throw new UnsupportedOperationException();
    }

    public static UUID getUUID() {
        throw new UnsupportedOperationException();
    }

    public static String getAccessToken() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable String getClientID() {
        throw new UnsupportedOperationException();
    }

    public static @Nullable String getXuid() {
        throw new UnsupportedOperationException();
    }

    public static void setNick(String value) {
        throw new UnsupportedOperationException();
    }

    public static void setUUID(UUID value) {
        throw new UnsupportedOperationException();
    }

    public static void setAccessToken(String value) {
        throw new UnsupportedOperationException();
    }
}
