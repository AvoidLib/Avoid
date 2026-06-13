package pl.olafcio.avoid;

import net.fabricmc.api.ModInitializer;

public final class A4Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Avoid.INSTANCE.onInitialize();
    }
}
