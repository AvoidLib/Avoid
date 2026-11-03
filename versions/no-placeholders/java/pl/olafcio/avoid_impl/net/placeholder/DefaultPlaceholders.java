package pl.olafcio.avoid_impl.net.placeholder;

import pl.olafcio.avoid.net.placeholder.PlaceholderProvider;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;

public enum DefaultPlaceholders {
    ;

    public static PlaceholderProvider get() {
        return new Impl_Dummy();
    }
}
