package pl.olafcio.avoid.mods.config;

public class ConfigurationIssue extends RuntimeException {
    public ConfigurationIssue() {
        super();
    }

    public ConfigurationIssue(String message) {
        super(message);
    }

    public ConfigurationIssue(String message, Throwable parent) {
        super(message, parent);
    }
}
