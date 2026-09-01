package pl.olafcio.avoid.mixininterface;

import net.minecraft.client.gui.components.Renderable;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.WidgetMarker;

@ApiStatus.NonExtendable
public interface IScreen {
    Renderable avoid$widget(WidgetMarker marker);
}
