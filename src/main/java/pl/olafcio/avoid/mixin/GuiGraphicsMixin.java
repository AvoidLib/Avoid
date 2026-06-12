package pl.olafcio.avoid.mixin;

import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.gui.event.RenderOutlineEvent;
import pl.olafcio.avoid.net.screen.DrawerNative;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
    @Inject(at = @At("HEAD"), method = "renderOutline", cancellable = true)
    public void renderOutline(int i, int j, int k, int l, int m, CallbackInfo ci) {
        var event = new RenderOutlineEvent(DrawerNative.create((GuiGraphics) (Object) this), i, j, k, l, m);

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
