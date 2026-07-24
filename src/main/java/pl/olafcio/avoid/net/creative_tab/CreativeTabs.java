package pl.olafcio.avoid.net.creative_tab;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.net.id.Identification;

public final class CreativeTabs {
    @ApiStatus.Internal
    private CreativeTabs() {}

    public static void register(Identification id, CreativeTab tab) {
        if (CreativeTabsNative.tabs == null) {
            throw new RuntimeException("Creative tab registration has already been finalized; " +
                                       "did you forget to register in AvoidMod#onLoad?");
        }

        if (CreativeTabsNative.tabs.containsKey(id)) {
            Avoid.LOGGER.error("The creative tab '{}' is already registered", id);
            return;
        }

        CreativeTabsNative.tabs.put(id, tab);
    }
}
