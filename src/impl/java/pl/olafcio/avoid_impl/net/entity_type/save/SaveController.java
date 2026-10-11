package pl.olafcio.avoid_impl.net.entity_type.save;

import com.mojang.serialization.Codec;
import org.apache.commons.lang3.function.FailableBiConsumer;

import java.lang.reflect.Field;
import java.util.function.Function;

public class SaveController<Avoid, Native, T extends Codec<Native>> {
    public final T codec;
    public final Function<Avoid, Native> converterTo;
    public final Function<Native, Avoid> converterFrom;

    public SaveController(T codec, Function<Avoid, Native> converterTo, Function<Native, Avoid> converterFrom) {
        this.codec = codec;
        this.converterTo = converterTo;
        this.converterFrom = converterFrom;
    }

    public static <T, S extends Codec<T>> SaveController<T, T, S> create(S codec) {
        return new SaveController<>(codec, Function.identity(), Function.identity());
    }

    //TODO Can this setter parameter be simplified?
    public static <T, S extends Codec<T>> SaveController<T, T, S> create(S codec, Function<Field, FailableBiConsumer<Object, T, IllegalAccessException>> setter) {
        return new SaveController<>(codec, Function.identity(), Function.identity()) {
            @Override
            public void set(Object parent, T value) throws IllegalAccessException {
                setter.apply(field).accept(parent, value);
            }
        };
    }

    public Field field;
    public String name;

    public void set(Object parent, Avoid value) throws IllegalAccessException {
        field.set(parent, value);
    }
}
