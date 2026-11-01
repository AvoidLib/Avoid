package pl.olafcio.avoid_impl.net.placeholder;

import pl.olafcio.avoid.net.placeholder.PlaceholderProvider;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;

public enum DefaultPlaceholders {
    ;

    public static PlaceholderProvider get() {
        if (AvoidWrappedLoader.isModPresent("placeholder-api"))
            return new Impl_TextPlaceholderAPI();
        else
            return new Impl_Dummy();
    }
}
