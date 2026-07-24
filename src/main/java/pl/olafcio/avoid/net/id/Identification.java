package pl.olafcio.avoid.net.id;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@NullMarked
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
    @WillRefactor(aspect = "name")
    public String toString() {
        return namespace + ":" + path;
    }

    @ApiStatus.Experimental
    public int hashCode() {
        return 31 * this.namespace.hashCode() + this.path.hashCode();
    }

    @ApiStatus.Experimental
    public boolean is(String namespace, String path) {
        return this.namespace.equals(namespace) && this.path.equals(path);
    }
}
