package pl.olafcio.avoid_impl.net.config.controller;

import pl.olafcio.avoid.net.config.annotations.YAML;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.function.Function;

public class ConfigController<Avoid, Native, Export> {
    public final Function<Native, Avoid> converterFrom;
    public final Function<Avoid, Export> converterTo;

    public ConfigController(
            Function<Native, Avoid> converterFrom,
            Function<Avoid, Export> converterTo
    ) {
        this.converterFrom = converterFrom;
        this.converterTo = converterTo;
    }

    public static <T, E> ConfigController<T, T, E> create(Function<T, E> writer) {
        return new ConfigController<>(Function.identity(), writer);
    }

    public Field field;
    public VarHandle varHandle;

    public YAML fieldYAML;

    public String name;

    public void set(Object parent, Avoid value) throws IllegalAccessException {
        varHandle.set(parent, value);
    }
}
