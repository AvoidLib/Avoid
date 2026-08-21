package pl.olafcio.avoid.net.item.component.values;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.StreamCodec;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public final class _value_type<O> {
    final Codec<?> codec;
    final StreamCodec<?, ?> streamCodec;
    final TransformingItemComponentValue<?, O> controller;

    _value_type(Class<?> input, TransformingItemComponentValue<?, O> controller) {
        try {
            this.codec = (Codec<?>) input.getDeclaredField("CODEC").get(null);
            this.streamCodec = (StreamCodec<?, ?>) input.getDeclaredField("STREAM_CODEC").get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("Failed to initialize value type", e);
        }

        this.controller = controller;
    }

    _value_type(Codec<?> codec, StreamCodec<?, ?> streamCodec, TransformingItemComponentValue<?, O> controller) {
        this.codec = codec;
        this.streamCodec = streamCodec;
        this.controller = controller;
    }
}
