package pl.olafcio.avoid.net.util;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class Maths {
    @ApiStatus.Internal
    private Maths() {}

    //======//
    // LERP //
    //======//

    public static float lerp(float from, float to, float progress) {
        return from*progress + to*(1-progress);
    }

    public static double lerp(double from, double to, float progress) {
        return from*progress + to*(1-progress);
    }

    public static double lerp(double from, double to, double progress) {
        return from*progress + to*(1-progress);
    }

    //=============//
    // INTERPOLATE //
    //=============//

    public static float interpolate(float from, float to, float progress, pl.olafcio.avoid.net.util.easing_float.Easing easing) {
        progress = easing.apply(progress);

        return from*progress + to*(1-progress);
    }

    public static double interpolate(double from, double to, float progress, pl.olafcio.avoid.net.util.easing_float.Easing easing) {
        progress = easing.apply(progress);

        return from*progress + to*(1-progress);
    }

    public static double interpolate(double from, double to, double progress, pl.olafcio.avoid.net.util.easing_double.Easing easing) {
        progress = easing.apply(progress);

        return from*progress + to*(1-progress);
    }

    //========//
    // RADIAL //
    //========//

    public static float radial(float val, float max) {
        if (val > max)
            val -= (val - max);

        return val;
    }

    public static double radial(double val, double max) {
        if (val > max)
            val -= (val - max);

        return val;
    }

    //===============//
    // RADIAL EASING //
    //===============//

    public static float radial(float val, float max, pl.olafcio.avoid.net.util.easing_float.Easing easing) {
        if (val > max)
            val -= (val - max);

        float div = val / max;
        return easing.apply(val / div) * div;
    }

    public static double radial(double val, double max, pl.olafcio.avoid.net.util.easing_double.Easing easing) {
        if (val > max)
            val -= (val - max);

        double div = val / max;
        return easing.apply(val / div) * div;
    }
}
