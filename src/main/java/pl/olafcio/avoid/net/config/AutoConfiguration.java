package pl.olafcio.avoid.net.config;

public interface AutoConfiguration {
    default void reload() {
        Configurations.getState(this.getClass()).reload();
    }

    default void save() {
        Configurations.getState(this.getClass()).save();
    }
}
