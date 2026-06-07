package pl.olafcio.avoid.net.screen.font;

import net.minecraft.client.gui.Font;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class FontNative {
    @ApiStatus.Internal
    private FontNative() {}

    public static Font convert(pl.olafcio.avoid.net.screen.font.Font font) {
        return font.font;
    }
}
