package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mixinclass.MyFormattedCharSequence;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.component.type.ParentComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.gui.event.RenderOutlineEvent;
import pl.olafcio.avoid.net.gui.event.RenderTextEvent;
import pl.olafcio.avoid.net.screen.DrawerNative;
import pl.olafcio.avoid.net.screen.font.FontNative;

// FIXME: Includes component-style hacks

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
    @Inject(at = @At("HEAD"), method = "renderOutline", cancellable = true)
    public void renderOutline(int i, int j, int k, int l, int m, CallbackInfo ci) {
        var event = new RenderOutlineEvent(DrawerNative.create((GuiGraphics) (Object) this), i, j, k, l, m);

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }

    @WrapMethod(method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V")
    public void drawString(Font font, FormattedCharSequence formattedCharSequence, int i, int j, int k, boolean bl, Operation<Void> original) {
        var component = ParentComponent.of();
        var array = component.getChildren();

        formattedCharSequence.accept((index, style, ch) -> {
            component.append(COFromNative.from(Component.literal(Character.toString((char) ch))
                                                        .withStyle(style)));
            return true;
        });

        var event = new RenderTextEvent(
                DrawerNative.create((GuiGraphics) (Object) this),
                FontNative.convertFrom(font),
                component,
                i, j, k, bl
        );

        EventManager.fire(event);

        if (event.isCancelled())
            return;

        original.call(FontNative.convert(event.font), new MyFormattedCharSequence(array), i, j, k, bl);
    }
}
