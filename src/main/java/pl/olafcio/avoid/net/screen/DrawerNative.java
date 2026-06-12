package pl.olafcio.avoid.net.screen;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@ApiStatus.Experimental
public final class DrawerNative {
    @ApiStatus.Internal
    private DrawerNative() {}

    public static Drawer create(GuiGraphics graphics) {
        var d = new Drawer();
        d.graphics = graphics;
        return d;
    }
}
