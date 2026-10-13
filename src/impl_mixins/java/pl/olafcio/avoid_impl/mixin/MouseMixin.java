package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.mouse.Mouse;
import pl.olafcio.avoid.net.mouse.event.ClientMouseDownEvent;

@Mixin(MouseHandler.class)
public abstract class MouseMixin {
    @Shadow public abstract double getScaledXPos(Window window);
    @Shadow public abstract double getScaledYPos(Window window);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0), method = "onButton", cancellable = true)
    public void onButton(long l, MouseButtonInfo mouseButtonInfo, int i, CallbackInfo ci, @Local Window window) {
        double x = this.getScaledXPos(window);
        double y = this.getScaledYPos(window);

        var button = mouseButtonInfo.button();
        var event = new ClientMouseDownEvent(
                x, y,
                button == 0 ? Mouse.LEFT   :
                button == 2 ? Mouse.MIDDLE :
                button == 1 ? Mouse.RIGHT  :
                button,
                mouseButtonInfo.modifiers()
        );

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
