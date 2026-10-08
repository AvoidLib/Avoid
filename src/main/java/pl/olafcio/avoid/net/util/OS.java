package pl.olafcio.avoid.net.util;

import net.minecraft.util.Util;
import org.jetbrains.annotations.ApiStatus;

import java.net.URI;

@ApiStatus.Experimental
public final class OS {
    @ApiStatus.Internal
    private OS() {}

    public static void openURI(URI uri) {
        Util.getPlatform().openUri(uri);
    }

    public static void openURI(String uri) {
        Util.getPlatform().openUri(URI.create(uri));
    }
}
