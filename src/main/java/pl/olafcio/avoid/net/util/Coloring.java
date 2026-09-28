package pl.olafcio.avoid.net.util;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

import java.awt.*;

@WillRefactor(aspect = "name")
public final class Coloring {
    @ApiStatus.Internal
    private Coloring() {}

    public static int toARGB(int rgb) {
        return rgb | -0x1000000;
    }

    public static int toARGB(Color color) {
        return color.getRGB();
    }

    public static int toRGB(int argb) {
        return argb + 0x1000000;
    }

    public static int toRGB(Color color) {
        return color.getRGB() + 0x1000000;
    }

    public static int getAlpha(int argb) {
        return argb >>> 24;
    }

    public static int getRed(int argb) {
        return argb >> 16 & 255;
    }

    public static int getGreen(int argb) {
        return argb >> 8 & 255;
    }

    public static int getBlue(int argb) {
        return argb & 255;
    }
}
