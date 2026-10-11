package pl.olafcio.avoid_impl.net.entity_type.sync;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.apache.commons.lang3.function.FailableBiConsumer;

import java.lang.reflect.Field;
import java.util.function.Function;

public class Controller<Avoid, Native, T extends EntityDataSerializer<Native>> {
    public final T serializer;
    public final Function<Avoid, Native> converterTo;
    public final Function<Native, Avoid> converterFrom;

    public Controller(T serializer, Function<Avoid, Native> converterTo, Function<Native, Avoid> converterFrom) {
        this.serializer = serializer;
        this.converterTo = converterTo;
        this.converterFrom = converterFrom;
    }

    public static <T, S extends EntityDataSerializer<T>> Controller<T, T, S> create(S serializer) {
        return new Controller<>(serializer, Function.identity(), Function.identity());
    }

    //TODO Can this setter parameter be simplified?
    public static <T, S extends EntityDataSerializer<T>> Controller<T, T, S> create(S serializer, Function<Field, FailableBiConsumer<Object, T, IllegalAccessException>> setter) {
        return new Controller<>(serializer, Function.identity(), Function.identity()) {
            @Override
            public void set(Object parent, T value) throws IllegalAccessException {
                setter.apply(field).accept(parent, value);
            }
        };
    }

    public EntityDataAccessor<?> accessor;
    public Field field;

    public void set(Object parent, Avoid value) throws IllegalAccessException {
        field.set(parent, value);
    }
}
