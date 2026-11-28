package pl.olafcio.avoid_impl.net.screen;

import net.minecraft.client.gui.components.Renderable;
import pl.olafcio.avoid.net.screen.Screen;
import pl.olafcio.avoid.net.screen.WidgetMarker;
import pl.olafcio.avoid_impl.mixininterface.IScreen;

public class AvoidScreen extends Screen implements IScreen {
    public Screen screen;
    public Object realScreen;

    public AvoidScreen(Screen screen) {
        super(screen.getAccessibilityTitle());
    }

    @Override
    public Renderable avoid$widget(WidgetMarker marker) {
        return null;
    }
}
