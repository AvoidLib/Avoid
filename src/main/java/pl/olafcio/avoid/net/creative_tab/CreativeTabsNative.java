package pl.olafcio.avoid.net.creative_tab;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.id.Identification;

import java.util.ArrayList;
import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class CreativeTabsNative {
    @ApiStatus.Internal
    private CreativeTabsNative() {}

    public static HashMap<Identification, CreativeTab> tabs
            = new HashMap<>();
}
