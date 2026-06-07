package pl.olafcio.avoid.net.util;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

import java.awt.*;

@WillRefactor(aspect = "name")
public final class Coloring {
    @ApiStatus.Internal
    private Coloring() {}

    public static int toARGB(int i) {
        return i | -0x1000000;
    }

    public static int toARGB(Color i) {
        return i.getRGB();
    }

    public static int toRGB(int i) {
        return i + 0x1000000;
    }

    public static int toRGB(Color i) {
        return i.getRGB() + 0x1000000;
    }

    public static int getAlpha(int i) {
        return i >>> 24;
    }

    public static int getRed(int i) {
        return i >> 16 & 255;
    }

    public static int getGreen(int i) {
        return i >> 8 & 255;
    }

    public static int getBlue(int i) {
        return i & 255;
    }
}
