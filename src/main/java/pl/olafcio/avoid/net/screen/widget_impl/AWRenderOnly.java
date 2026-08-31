package pl.olafcio.avoid.net.screen.widget_impl;

import net.minecraft.client.gui.GuiGraphics;
import pl.olafcio.avoid.net.screen.DrawerNative;
import pl.olafcio.avoid.net.screen.widget.able.Renderable;

public record AWRenderOnly(Renderable renderable)
       implements net.minecraft.client.gui.components.Renderable,
                  IAvoidWidget
{
    @Override
    public Renderable getAvoid() {
        return renderable;
    }

    //==================//
    //=== RENDERABLE ===//
    //==================//

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        renderable.render(DrawerNative.create(guiGraphics), i, j, f);
    }
}
