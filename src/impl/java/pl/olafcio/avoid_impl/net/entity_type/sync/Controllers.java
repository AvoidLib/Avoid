package pl.olafcio.avoid_impl.net.entity_type.sync;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3fc;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid_impl.net._3d.DirectionNative;
import pl.olafcio.avoid_impl.net.block.pos.BlockPosNative;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid_impl.net.world.block_data.BlockDataNative;

import java.lang.reflect.Field;

public final class Controllers {
    @ApiStatus.Internal
    private Controllers() {}

    public static Controller<?, ?, ?> convert(Field field) {
        var klass = field.getType();
        var ret = convert(klass);

        ret.field = field;

        return ret;
    }

    public static Controller<?, ?, ?> convert(Class<?> klass) {
        EntityDataSerializer<?> x;

             if (klass == String.class)                            x = EntityDataSerializers.STRING;
        else if (klass == byte.class    || klass == Byte.class)    return Controller.create(EntityDataSerializers.BYTE, f -> f::setByte);
        else if (klass == int.class     || klass == Integer.class) return Controller.create(EntityDataSerializers.INT, f -> f::setInt);
        else if (klass == float.class   || klass == Float.class)   return Controller.create(EntityDataSerializers.FLOAT, f -> f::setFloat);
        else if (klass == long.class    || klass == Long.class)    return Controller.create(EntityDataSerializers.LONG, f -> f::setLong);
        else if (klass == boolean.class || klass == Boolean.class) return Controller.create(EntityDataSerializers.BOOLEAN, f -> f::setBoolean);
        else if (klass == Direction.class)                       return new Controller<>(EntityDataSerializers.DIRECTION, DirectionNative::convertFrom, DirectionNative::convert);
        else if (BaseComponent.class.isAssignableFrom(klass))    return new Controller<>(EntityDataSerializers.COMPONENT, COToNative::from, COFromNative::from);
        else if (klass == BlockPos.class)                        return new Controller<>(EntityDataSerializers.BLOCK_POS, BlockPosNative::convertFrom, BlockPosNative::convert);
        else if (BlockData.class.isAssignableFrom(klass))        return new Controller<>(EntityDataSerializers.BLOCK_STATE, BlockDataNative::convert, BlockDataNative::convertFrom);
        else if (Vector3fc.class.isAssignableFrom(klass))        x = EntityDataSerializers.VECTOR3;
        else                                                     throw new RuntimeException("Unserializable type '%s'".formatted(klass.getName()));

        return Controller.create(x);
    }
}
