package pl.olafcio.avoid.net.id;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@NeverRemoval
public record Identification(String namespace, String path) {
    @NeverRemoval
    public Identification(String path) {
        this("minecraft", path);
    }

    @NeverRemoval
    public static Identification of(String value) {
        int sep = value.indexOf(':');
        if (sep == -1)
            return new Identification(value);

        String namespace = value.substring(0, sep);
        String path = value.substring(sep + 1);

        return new Identification(namespace, path);
    }

    @Override
    @NotNull
    @WillRefactor(aspect = "name")
    public String toString() {
        return namespace + ":" + path;
    }
}
