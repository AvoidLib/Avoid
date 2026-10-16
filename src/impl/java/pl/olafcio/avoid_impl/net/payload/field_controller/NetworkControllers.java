package pl.olafcio.avoid_impl.net.payload.field_controller;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.payload.field.PacketField;
import pl.olafcio.avoid.net.payload.field.processor.FieldProcessor;
import pl.olafcio.avoid.net.payload.field.processor.Type;
import pl.olafcio.avoid_impl.net.block.pos.BlockPosNative;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

import java.lang.reflect.Field;
import java.util.UUID;

public final class NetworkControllers {
    private static final NetworkController<String, String> UTF = NetworkController.create(FriendlyByteBuf::readUtf, FriendlyByteBuf::writeUtf);
    private static final NetworkController<Byte, Byte> BYTE = NetworkController.create(FriendlyByteBuf::readByte, (f, b) -> f.writeByte(b), f -> f::setByte);
    private static final NetworkController<Integer, Integer> INT = NetworkController.create(FriendlyByteBuf::readInt, FriendlyByteBuf::writeInt, f -> f::setInt);
    private static final NetworkController<Character, Character> CHAR = NetworkController.create(FriendlyByteBuf::readChar, (f, c) -> f.writeChar(c), f -> f::setChar);
    private static final NetworkController<Float, Float> FLOAT = NetworkController.create(FriendlyByteBuf::readFloat, FriendlyByteBuf::writeFloat, f -> f::setFloat);
    private static final NetworkController<Long, Long> LONG = NetworkController.create(FriendlyByteBuf::readLong, FriendlyByteBuf::writeLong, f -> f::setLong);
    private static final NetworkController<Short, Short> SHORT = NetworkController.create(FriendlyByteBuf::readShort, (f, s) -> f.writeShort(s), f -> f::setShort);
    private static final NetworkController<Double, Double> DOUBLE = NetworkController.create(FriendlyByteBuf::readDouble, FriendlyByteBuf::writeDouble, f -> f::setDouble);
    private static final NetworkController<Boolean, Boolean> BOOLEAN = NetworkController.create(FriendlyByteBuf::readBoolean, FriendlyByteBuf::writeBoolean, f -> f::setBoolean);
    private static final NetworkController<byte[], byte[]> BYTE_ARRAY = NetworkController.create(f -> f.readByteArray(), (f, bs) -> f.writeByteArray(bs));
    private static final NetworkController<long[], long[]> LONG_ARRAY = NetworkController.create(f -> f.readLongArray(), (f, ls) -> f.writeLongArray(ls));
    private static final NetworkController<UUID, UUID> UUID = NetworkController.create(f -> f.readUUID(), (f, u) -> f.writeUUID(u));
    private static final NetworkController<BlockPos, net.minecraft.core.BlockPos> BLOCK_POS = new NetworkController<>(f -> f.readBlockPos(), (f, b) -> f.writeBlockPos(b), BlockPosNative::convertFrom, BlockPosNative::convert);
    private static final NetworkController<Identification, Identifier> IDENTIFICATION = new NetworkController<>(FriendlyByteBuf::readIdentifier, FriendlyByteBuf::writeIdentifier, IdentificationNative::convert, IdentificationNative::convertFrom);

    @ApiStatus.Internal
    private NetworkControllers() {}

    public static NetworkController<?, ?> convert(Field field) {
        NetworkController<?, ?> ret;

        var processor = field.getDeclaredAnnotation(PacketField.class).value();
        if (processor != FieldProcessor.__AUTO__) {
            try {
                var expect = FieldProcessor.class.getDeclaredField(processor.name())
                                               .getDeclaredAnnotation(Type.class)
                                               .value();

                if (expect != field.getType())
                    throw new RuntimeException("Unmatching field processor '%s' on '%s#%s': expected field type %s, got %s".formatted(processor.name(), field.getName(), field.getDeclaringClass().getName(), expect.getName(), field.getType().getName()));

            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Failed to test field processor '%s'".formatted(processor.name()), e);
            }

            ret = convert(processor);
        } else {
            var klass = field.getType();

            ret = convert(klass);
        }

        ret.field = field;
        ret.name = field.getName();

        return ret;
    }

    public static NetworkController<?, ?> convert(Class<?> klass) {
             if (klass == String.class)                              return UTF;
        else if (klass == byte.class    || klass == Byte.class)      return BYTE;
        else if (klass == int.class     || klass == Integer.class)   return INT;
        else if (klass == char.class    || klass == Character.class) return CHAR;
        else if (klass == float.class   || klass == Float.class)     return FLOAT;
        else if (klass == long.class    || klass == Long.class)      return LONG;
        else if (klass == short.class   || klass == Short.class)     return SHORT;
        else if (klass == double.class  || klass == Double.class)    return DOUBLE;
        else if (klass == boolean.class || klass == Boolean.class)   return BOOLEAN;
        else if (byte[].class.isAssignableFrom(klass))               return BYTE_ARRAY;
        else if (long[].class.isAssignableFrom(klass))               return LONG_ARRAY;
        else if (klass == UUID.class)                                return UUID;
        else if (klass == BlockPos.class)                            return BLOCK_POS;
        else if (klass == Identification.class)                      return IDENTIFICATION;
        else                                                         throw new RuntimeException("Unserializable type '%s'".formatted(klass.getName()));
    }

    public static NetworkController<?, ?> convert(FieldProcessor processor) {
             if (processor == FieldProcessor.VAR_INT)            return NetworkController.create(FriendlyByteBuf::readVarInt, FriendlyByteBuf::writeVarInt);
        else if (processor == FieldProcessor.VAR_LONG)           return NetworkController.create(FriendlyByteBuf::readVarLong, FriendlyByteBuf::writeVarLong);
        else if (processor == FieldProcessor.VAR_INT_ARRAY)      return NetworkController.create(FriendlyByteBuf::readVarIntArray, FriendlyByteBuf::writeVarIntArray);
        else if (processor == FieldProcessor.MEDIUM)             return NetworkController.create(FriendlyByteBuf::readMedium, FriendlyByteBuf::writeMedium);
        else if (processor == FieldProcessor.MEDIUM_LE)          return NetworkController.create(FriendlyByteBuf::readMediumLE, FriendlyByteBuf::writeMediumLE);
        else if (processor == FieldProcessor.UNSIGNED_MEDIUM)    return NetworkController.create(FriendlyByteBuf::readUnsignedMedium, FriendlyByteBuf::writeMedium);
        else if (processor == FieldProcessor.UNSIGNED_MEDIUM_LE) return NetworkController.create(FriendlyByteBuf::readUnsignedMediumLE, FriendlyByteBuf::writeMediumLE);
        else if (processor == FieldProcessor.UNSIGNED_SHORT)     return NetworkController.create(FriendlyByteBuf::readUnsignedShort, FriendlyByteBuf::writeShort);
        else if (processor == FieldProcessor.UNSIGNED_SHORT_LE)  return NetworkController.create(FriendlyByteBuf::readUnsignedShortLE, FriendlyByteBuf::writeShortLE);
        else if (processor == FieldProcessor.UNSIGNED_INT)       return NetworkController.create(FriendlyByteBuf::readUnsignedInt, FriendlyByteBuf::writeLong);
        else if (processor == FieldProcessor.UNSIGNED_INT_LE)    return NetworkController.create(FriendlyByteBuf::readUnsignedIntLE, FriendlyByteBuf::writeLongLE);
        else if (processor == FieldProcessor.UNSIGNED_BYTE)      return NetworkController.create(FriendlyByteBuf::readUnsignedByte, (f, s) -> f.writeByte(s));
        else                                                     throw new RuntimeException("Invalid field-processor '%s'".formatted(processor.name()));
    }
}
