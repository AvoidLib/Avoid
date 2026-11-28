package pl.olafcio.avoid.net.client_loading.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.screen.Drawer;

import java.util.function.Supplier;

/**
 * An event fired when the Mojang Logo on the loading screen is rendered.
 */
public final class ClientLoadingScreenRenderMojangEvent extends Cancellable {
    private final Supplier<Drawer> drawerSupplier;

    @Discouraged(reason = "Meant to be internal.")
    @ApiStatus.Experimental
    public ClientLoadingScreenRenderMojangEvent(Supplier<Drawer> drawerSupplier) {
        this.drawerSupplier = drawerSupplier;
    }

    public Drawer ctx() {
        return drawerSupplier.get();
    }
}
