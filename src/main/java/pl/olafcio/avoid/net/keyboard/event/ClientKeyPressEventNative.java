package pl.olafcio.avoid.net.keyboard.event;

import net.minecraft.client.input.KeyEvent;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class ClientKeyPressEventNative {
    @ApiStatus.Internal
    private ClientKeyPressEventNative() {}

    public static ClientKeyPressEvent create() {
        return new ClientKeyPressEvent();
    }

    public static void change(ClientKeyPressEvent avoidevent, KeyEvent mcevent) {
        avoidevent.event = mcevent;
    }
}
