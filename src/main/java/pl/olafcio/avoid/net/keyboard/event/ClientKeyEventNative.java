package pl.olafcio.avoid.net.keyboard.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.KeyEvent;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class ClientKeyEventNative {
    @ApiStatus.Internal
    private ClientKeyEventNative() {}

    public static ClientKeyPressEvent createPress() {
        return new ClientKeyPressEvent();
    }

    public static ClientKeyReleaseEvent createRelease() {
        return new ClientKeyReleaseEvent();
    }

    public static void change(ClientKeyEvent avoidevent, KeyEvent mcevent) {
        avoidevent.key = mcevent.key();
        avoidevent.scancode = mcevent.scancode();
        avoidevent.modifiers = mcevent.modifiers();
    }
}
