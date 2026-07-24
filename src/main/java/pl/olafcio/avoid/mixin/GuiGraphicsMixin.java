package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mixinclass.MyFormattedCharSequence;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.component.type.ParentComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.gui.event.RenderOutlineEvent;
import pl.olafcio.avoid.net.gui.event.RenderTextEvent;
import pl.olafcio.avoid.net.resource.ResourcesNative;
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
            component.append(COFromNative.from(Component.literal(Character.toString(ch))
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

        original.call(FontNative.convert(event.getFont()), new MyFormattedCharSequence(array), event.x, event.y, event.color, event.shadow);
    }

    @ModifyVariable(at = @At("HEAD"), method = {"innerBlit", "blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIIII)V"}, argsOnly = true)
    public Identifier blit(Identifier id) {
        int hash = id.hashCode();
        if (ResourcesNative.replacements.containsKey(hash))
            return ResourcesNative.replacements.get(hash);

        return id;
    }
}
