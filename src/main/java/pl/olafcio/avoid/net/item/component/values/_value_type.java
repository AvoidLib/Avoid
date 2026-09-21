package pl.olafcio.avoid.net.item.component.values;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.StreamCodec;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Arrays;

public final class _value_type<O> {
    final Codec<?> codec;
    final StreamCodec<?, ?> streamCodec;
    final TransformingItemComponentValue<?, O> controller;

    _value_type(Class<?> input, TransformingItemComponentValue<?, O> controller) {
        try {
            this.codec = findFirstField(input, Codec.class);
            this.streamCodec = findFirstField(input, StreamCodec.class);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to initialize value type", e);
        }

        this.controller = controller;
    }

    @SuppressWarnings("unchecked")
    private static <T> T findFirstField(Class<?> input, Class<T> search) throws IllegalAccessException {
        var field1 = Arrays.stream(input.getDeclaredFields())
                           .filter(field -> search.isAssignableFrom(field.getType()))
                           .findAny()
                           .orElseThrow();

        return (T) MethodHandles.privateLookupIn(input, MethodHandles.lookup()).unreflectVarHandle(field1).get();
    }

    _value_type(Codec<?> codec, StreamCodec<?, ?> streamCodec, TransformingItemComponentValue<?, O> controller) {
        this.codec = codec;
        this.streamCodec = streamCodec;
        this.controller = controller;
    }
}
