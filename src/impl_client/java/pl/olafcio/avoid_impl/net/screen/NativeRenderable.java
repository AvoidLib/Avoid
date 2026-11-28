package pl.olafcio.avoid_impl.net.screen;

import pl.olafcio.avoid.net.screen.widget.able.Renderable;

public interface NativeRenderable extends Renderable {
    net.minecraft.client.gui.components.Renderable element();
}
