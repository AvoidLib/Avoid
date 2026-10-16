package pl.olafcio.avoid_impl.net.payload.field_controller;

import net.minecraft.network.FriendlyByteBuf;
import org.apache.commons.lang3.function.FailableBiConsumer;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NetworkController<Avoid, Native> {
    public final Function<FriendlyByteBuf, Native> reader;
    public final BiConsumer<FriendlyByteBuf, Native> writer;
    public final Function<Avoid, Native> converterTo;
    public final Function<Native, Avoid> converterFrom;

    public NetworkController(
            Function<FriendlyByteBuf, Native> reader,
            BiConsumer<FriendlyByteBuf, Native> writer,

            Function<Avoid, Native> converterTo,
            Function<Native, Avoid> converterFrom
    ) {
        this.reader = reader;
        this.writer = writer;
        this.converterTo = converterTo;
        this.converterFrom = converterFrom;
    }

    public static <T> NetworkController<T, T> create(
            Function<FriendlyByteBuf, T> reader,
            BiConsumer<FriendlyByteBuf, T> writer
    ) {
        return new NetworkController<>(reader, writer, Function.identity(), Function.identity());
    }

    //TODO Can this setter parameter be simplified?
    public static <T> NetworkController<T, T> create(
            Function<FriendlyByteBuf, T> reader,
            BiConsumer<FriendlyByteBuf, T> writer,

            Function<Field, FailableBiConsumer<Object, T, IllegalAccessException>> setter
    ) {
        return new NetworkController<>(reader, writer, Function.identity(), Function.identity()) {
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
