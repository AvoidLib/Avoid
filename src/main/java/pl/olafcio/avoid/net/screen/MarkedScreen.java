package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.screen.widget.able.Renderable;

@ApiStatus.Experimental
public interface MarkedScreen {
    @Nullable
    Renderable widget(WidgetMarker marker);
}
