package pl.olafcio.avoid;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class A4 {
    public static void init(Screen screen) {
        throw new RuntimeException("Paper servers don't have screens");
    }
}
