package pl.olafcio.avoid.mods.config;

import org.jetbrains.annotations.ApiStatus;

public final class Numbers {
    @ApiStatus.Internal
    private Numbers() {}

    public static Integer toInt(Object value) {
        if (value instanceof Number n)
            return n.intValue();
        else
            return Integer.parseInt(String.valueOf(value));
    }

    public static Float toFloat(Object value) {
        if (value instanceof Number n)
            return n.floatValue();
        else
            return Float.parseFloat(String.valueOf(value));
    }

    public static Long toLong(Object value) {
        if (value instanceof Number n)
            return n.longValue();
        else
            return Long.parseLong(String.valueOf(value));
    }

    public static Short toShort(Object value) {
        if (value instanceof Number n)
            return n.shortValue();
        else
            return Short.parseShort(String.valueOf(value));
    }

    public static Byte toByte(Object value) {
        if (value instanceof Number n)
            return n.byteValue();
        else
            return Byte.parseByte(String.valueOf(value));
    }

    public static Double toDouble(Object value) {
        if (value instanceof Number n)
            return n.doubleValue();
        else
            return Double.parseDouble(String.valueOf(value));
    }
}
