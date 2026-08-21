package pl.olafcio.avoid.net.item.component.values;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

@Native
@ApiStatus.Internal
public final class _value_type_accessor {
    @ApiStatus.Internal
    private _value_type_accessor() {}

    public static Codec<?> getCodec(_value_type<?> type) {
        return type.codec;
    }

    public static StreamCodec<?, ?> getStreamCodec(_value_type<?> type) {
        return type.streamCodec;
    }

    public static <O> TransformingItemComponentValue<?, O> getController(_value_type<O> type) {
        return type.controller;
    }
}
