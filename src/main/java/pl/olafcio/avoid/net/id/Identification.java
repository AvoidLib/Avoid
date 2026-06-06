package pl.olafcio.avoid.net.id;

public record Identification(String namespace, String path) {
    public Identification(String path) {
        this("minecraft", path);
    }

    public static Identification of(String value) {
        int sep = value.indexOf(':');
        if (sep == -1)
            return new Identification(value);

        String namespace = value.substring(0, sep);
        String path = value.substring(sep + 1);

        return new Identification(namespace, path);
    }
}
