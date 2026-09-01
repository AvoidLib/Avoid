package pl.olafcio.avoid.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.net.screen.WidgetMarker;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen implements IScreen {
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

    @Unique
    Renderable singleplayer,
               multiplayer,
               realms,
               options,
               quit;

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 0), method = "createNormalMenuOptions")
    public GuiEventListener createNormalMenuOptions__addRenderableWidget__0(TitleScreen instance, GuiEventListener guiEventListener, Operation<GuiEventListener> original) {
        return (GuiEventListener) (singleplayer = (Renderable)original.call(instance, guiEventListener));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 1), method = "createNormalMenuOptions")
    public GuiEventListener createNormalMenuOptions__addRenderableWidget__1(TitleScreen instance, GuiEventListener guiEventListener, Operation<GuiEventListener> original) {
        return (GuiEventListener) (multiplayer = (Renderable)original.call(instance, guiEventListener));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 2), method = "createNormalMenuOptions")
    public GuiEventListener createNormalMenuOptions__addRenderableWidget__2(TitleScreen instance, GuiEventListener guiEventListener, Operation<GuiEventListener> original) {
        return (GuiEventListener) (realms = (Renderable)original.call(instance, guiEventListener));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 1), method = "init")
    public GuiEventListener init__addRenderableWidget__1(TitleScreen instance, GuiEventListener guiEventListener, Operation<GuiEventListener> original) {
        return (GuiEventListener) (options = (Renderable)original.call(instance, guiEventListener));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 2), method = "init")
    public GuiEventListener init__addRenderableWidget__2(TitleScreen instance, GuiEventListener guiEventListener, Operation<GuiEventListener> original) {
        return (GuiEventListener) (quit = (Renderable)original.call(instance, guiEventListener));
    }

    @Override
    public Renderable avoid$widget(WidgetMarker marker) {
        if (marker == WidgetMarker.TitleScreen.SINGLEPLAYER) {
            return singleplayer;
        } else if (marker == WidgetMarker.TitleScreen.MULTIPLAYER) {
            return multiplayer;
        } else if (marker == WidgetMarker.TitleScreen.REALMS) {
            return realms;
        } else if (marker == WidgetMarker.TitleScreen.OPTIONS) {
            return options;
        } else if (marker == WidgetMarker.TitleScreen.QUIT_GAME) {
            return quit;
        }

        return null;
    }
}
