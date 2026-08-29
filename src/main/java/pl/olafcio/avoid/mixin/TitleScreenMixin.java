package pl.olafcio.avoid.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.Avoid;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow private boolean fading;
    @Shadow private long fadeInStart;

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V", shift = At.Shift.AFTER), method = "render")
    public void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        float g = 1.0F;
        if (this.fading) {
            float h = (float)(Util.getMillis() - this.fadeInStart) / 2000.0F;
            if (h <= 1.0F) {
                h = Mth.clamp(h, 0.0F, 1.0F);
                g = Mth.clampedMap(h, 0.5F, 1.0F, 0.0F, 1.0F);
            }
        }

        var string = "Avoid " + Avoid.getVersion();

        guiGraphics.drawString(this.font, string, 2, this.height - 20, ARGB.white(g));
    }
}
