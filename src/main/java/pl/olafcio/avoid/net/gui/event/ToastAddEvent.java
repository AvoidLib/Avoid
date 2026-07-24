package pl.olafcio.avoid.net.gui.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.screen.Drawer;

@ApiStatus.Experimental
public final class ToastAddEvent extends Cancellable {
    private final Class<?> klass;
    private final String name;

    public ToastAddEvent(Class<?> klass, Object token) {
        this.klass = klass;
        this.name = String.valueOf(token);
    }

    public Class<?> getKlass() {
        return klass;
    }
    public String   getName () {
        return name ;
    }
}
