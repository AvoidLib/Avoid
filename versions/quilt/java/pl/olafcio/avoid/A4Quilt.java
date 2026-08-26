package pl.olafcio.avoid;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public final class A4Quilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        Avoid.INSTANCE.onInitialize();
    }
}
