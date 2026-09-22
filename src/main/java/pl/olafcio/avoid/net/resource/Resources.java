package pl.olafcio.avoid.net.resource;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid_impl.net.resource.ResourcesNative;

/**
 * <span style="color: #89b03c">
 * TODO Please fix this.<br/>
 * &emsp;&emsp;&emsp;The overriding doesn't work half of the time.
 * </span>
 */
@ApiStatus.Experimental
public final class Resources {
    @ApiStatus.Internal
    private Resources() {}

    public static void override(Identification find, Identification replacement) {
        ResourcesNative.replacements.put(find.hashCode(), IdentificationNative.convert(replacement));
    }

    public static void unoverride(Identification find) {
        ResourcesNative.replacements.remove(find.hashCode());
    }
}
