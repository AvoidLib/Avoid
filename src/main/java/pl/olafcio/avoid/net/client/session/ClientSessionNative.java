package pl.olafcio.avoid.net.client.session;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
@ApiStatus.Internal
final class ClientSessionNative {
    @ApiStatus.Internal
    private ClientSessionNative() {}

    public static String getNick() {
        return Minecraft.getInstance().getUser().getName();
    }

    public static UUID getUUID() {
        return Minecraft.getInstance().getUser().getProfileId();
    }

    public static String getAccessToken() {
        return Minecraft.getInstance().getUser().getAccessToken();
    }

    @Nullable
    public static String getClientID() {
        return Minecraft.getInstance().getUser().getClientId().orElse(null);
    }

    @Nullable
    public static String getXuid() {
        return Minecraft.getInstance().getUser().getXuid().orElse(null);
    }

    public static void setNick(String value) {
        Minecraft.getInstance().getUser().name = value;
    }

    public static void setUUID(UUID value) {
        Minecraft.getInstance().getUser().uuid = value;
    }

    public static void setAccessToken(String value) {
        Minecraft.getInstance().getUser().accessToken = value;
    }
}
