package pl.olafcio.avoid_impl.net.config.controller.abst;

import pl.olafcio.avoid_impl.net.config.controller.ConfigController;

public abstract class AbstractConfigController<Avoid, Native, Export> {
    public final ConfigController<Avoid, Native, Export> controller;

    public AbstractConfigController() {
        this.controller = new ConfigController<>(this::convertFrom, this::convertTo);
    }

    public abstract Avoid convertFrom(Native value);
    public abstract Export convertTo(Avoid value);
}
