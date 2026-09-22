package pl.olafcio.avoid_impl;

import net.fabricmc.api.ModInitializer;
import pl.olafcio.avoid.Avoid;

public final class A4Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Avoid.INSTANCE.onInitialize();
    }
}
