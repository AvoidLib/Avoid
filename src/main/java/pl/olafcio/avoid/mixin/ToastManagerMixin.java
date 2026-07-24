package pl.olafcio.avoid.mixin;

import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.gui.event.ToastAddEvent;

@Mixin(ToastManager.class)
public class ToastManagerMixin {
    @Inject(at = @At("HEAD"), method = "addToast", cancellable = true)
    public void addToast(Toast toast, CallbackInfo ci) {
        var event = new ToastAddEvent(toast.getClass(), toast.getToken());

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
