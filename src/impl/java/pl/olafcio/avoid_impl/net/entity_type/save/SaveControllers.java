package pl.olafcio.avoid_impl.net.entity_type.save;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public final class SaveControllers {
    @ApiStatus.Internal
    private SaveControllers() {}

    public static SaveController<?, ?, ?> convert(Field field) {
        var klass = field.getType();
        var ret = convert(klass);

        ret.field = field;
        ret.name = field.getDeclaringClass().getName() + "#" + field.getName();

        return ret;
    }

    public static SaveController<?, ?, ?> convert(Class<?> klass) {
        Codec<?> x;

             if (klass == String.class)                            x = Codec.STRING;
        else if (klass == byte.class    || klass == Byte.class)    return SaveController.create(Codec.BYTE, f -> f::setByte);
        else if (klass == int.class     || klass == Integer.class) return SaveController.create(Codec.INT, f -> f::setInt);
        else if (klass == float.class   || klass == Float.class)   return SaveController.create(Codec.FLOAT, f -> f::setFloat);
        else if (klass == long.class    || klass == Long.class)    return SaveController.create(Codec.LONG, f -> f::setLong);
        else if (klass == short.class   || klass == Short.class)   return SaveController.create(Codec.SHORT, f -> f::setShort);
        else if (klass == double.class  || klass == Double.class)  return SaveController.create(Codec.DOUBLE, f -> f::setDouble);
        else if (klass == boolean.class || klass == Boolean.class) return SaveController.create(Codec.BOOL, f -> f::setBoolean);
        else if (ByteBuffer.class.isAssignableFrom(klass))         x = Codec.BYTE_BUFFER;
        else                                                     throw new RuntimeException("Unserializable type '%s'".formatted(klass.getName()));

        return SaveController.create(x);
    }
}
