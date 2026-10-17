package pl.olafcio.avoid.mods.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface Configuration {
    Configuration getSection(String path);

    String getString(String path);
    Integer getInteger(String path);
    Float getFloat(String path);
    Long getLong(String path);
    Short getShort(String path);
    Byte getByte(String path);
    Double getDouble(String path);

    default List<String> getStringList(String path) {
        return getListOf(path, String.class);
    }

    default List<Integer> getIntList(String path) {
        return getListOf(path, Integer.class, Numbers::toInt);
    }

    default List<Float> getFloatList(String path) {
        return getListOf(path, Float.class, Numbers::toFloat);
    }

    default List<Long> getLongList(String path) {
        return getListOf(path, Long.class, Numbers::toLong);
    }

    default List<Short> getShortList(String path) {
        return getListOf(path, Short.class, Numbers::toShort);
    }

    default List<Byte> getByteList(String path) {
        return getListOf(path, Byte.class, Numbers::toByte);
    }

    default List<Double> getDoubleList(String path) {
        return getListOf(path, Double.class, Numbers::toDouble);
    }

        List<?> getList(String path);
    <T> List<T> getListOf(String path, Class<T> elementType);
    <T> List<T> getListOf(String path, Class<T> elementType, Function<Object, T> elementConverter);

    Map<String, Object> put(String path, Object value);

    default void putString(String path, String value) {
        put(path, value);
    }

    default void putInteger(String path, int value) {
        put(path, value);
    }

    default void putFloat(String path, float value) {
        put(path, value);
    }

    default void putLong(String path, long value) {
        put(path, value);
    }

    default void putShort(String path, short value) {
        put(path, value);
    }

    default void putByte(String path, byte value) {
        put(path, value);
    }

    default void putDouble(String path, double value) {
        put(path, value);
    }

    Object remove(String path);
}
