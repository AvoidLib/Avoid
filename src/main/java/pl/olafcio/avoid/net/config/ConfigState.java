package pl.olafcio.avoid.net.config;

public abstract class ConfigState<T> {
    public abstract T getInstance();

    public abstract void reload();
    public abstract void save();
}
