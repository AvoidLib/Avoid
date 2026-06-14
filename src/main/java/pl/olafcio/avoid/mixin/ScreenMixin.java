package pl.olafcio.avoid.mixin;

import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.screen.NativeScreenNative;
import pl.olafcio.avoid.net.screen.event.ScreenInitEvent;

@Mixin(Screen.class)
public class ScreenMixin
       implements IScreen
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;init()V"), method = "init(II)V")
    public void init(int i, int j, CallbackInfo ci) {
        EventManager.fire(new ScreenInitEvent(NativeScreenNative.create(this), i, j));
    }
}
