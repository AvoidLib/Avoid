package pl.olafcio.avoid_impl.net.payload.field_controller;

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;

public final class NetworkControllers {
    @ApiStatus.Internal
    private NetworkControllers() {}

    public static NetworkController<?, ?> convert(Field field) {
        var klass = field.getType();
        var ret = convert(klass);

        ret.field = field;
        ret.name = field.getName();

        return ret;
    }

    public static NetworkController<?, ?> convert(Class<?> klass) {
             if (klass == String.class)                            return NetworkController.create(FriendlyByteBuf::readUtf, FriendlyByteBuf::writeUtf);
        else if (klass == byte.class    || klass == Byte.class)    return NetworkController.create(FriendlyByteBuf::readByte, (f, b) -> f.writeByte(b), f -> f::setByte);
        else if (klass == int.class     || klass == Integer.class) return NetworkController.create(FriendlyByteBuf::readInt, FriendlyByteBuf::writeInt, f -> f::setInt);
        else if (klass == float.class   || klass == Float.class)   return NetworkController.create(FriendlyByteBuf::readFloat, FriendlyByteBuf::writeFloat, f -> f::setFloat);
        else if (klass == long.class    || klass == Long.class)    return NetworkController.create(FriendlyByteBuf::readLong, FriendlyByteBuf::writeLong, f -> f::setLong);
        else if (klass == short.class   || klass == Short.class)   return NetworkController.create(FriendlyByteBuf::readShort, (f, s) -> f.writeShort(s), f -> f::setShort);
        else if (klass == double.class  || klass == Double.class)  return NetworkController.create(FriendlyByteBuf::readDouble, FriendlyByteBuf::writeDouble, f -> f::setDouble);
        else if (klass == boolean.class || klass == Boolean.class) return NetworkController.create(FriendlyByteBuf::readBoolean, FriendlyByteBuf::writeBoolean, f -> f::setBoolean);
        else if (byte[].class.isAssignableFrom(klass))             return NetworkController.create(f -> f.readByteArray(), (f, bs) -> f.writeByteArray(bs));
        else                                                       throw new RuntimeException("Unserializable type '%s'".formatted(klass.getName()));
    }
}
