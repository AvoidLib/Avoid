package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.client.event.ClientScreenInPortalEvent;
import pl.olafcio.avoid.net.screen.NativeScreenNative;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;isPauseScreen()Z"), method = "handlePortalTransitionEffect")
    public boolean handlePortalTransitionEffect__isAllowedInPortal(Screen instance, Operation<Boolean> original) {
        var event = new ClientScreenInPortalEvent(
                NativeScreenNative.create((IScreen) instance),
                !(original.call(instance) || this.minecraft.screen instanceof DeathScreen || this.minecraft.screen instanceof WinScreen)
        );

        EventManager.fire(event);

        return !event.allowed();
    }

    @ModifyConstant(constant = {
            @Constant(classValue = DeathScreen.class),
            @Constant(classValue = WinScreen.class)
    }, method = "handlePortalTransitionEffect")
    public Class<?> handlePortalTransitionEffect__uselessConstant(Object instance, Class<?> type) {
        return Void.class;
    }
}
