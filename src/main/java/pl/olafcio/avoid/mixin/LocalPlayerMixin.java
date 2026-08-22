package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.client.event.ClientScreenInPortalEvent;
import pl.olafcio.avoid.net.screen.NativeScreenNative;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;isAllowedInPortal()Z"), method = "handlePortalTransitionEffect")
    public boolean handlePortalTransitionEffect__isAllowedInPortal(Screen instance, Operation<Boolean> original) {
        var event = new ClientScreenInPortalEvent(
                NativeScreenNative.create((IScreen) instance),
                original.call(instance)
        );

        EventManager.fire(event);

        return event.allowed();
    }
}
