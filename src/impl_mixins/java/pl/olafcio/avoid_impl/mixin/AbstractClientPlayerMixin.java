package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.client.event.ClientFovEvent;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    @Inject(at = @At("TAIL"), method = "getFieldOfViewModifier")
    public void getFieldOfViewModifier(
            boolean bl,
            float __,
            CallbackInfoReturnable<Float> cir,
            @Local(ordinal = 1) LocalFloatRef g,
            @Local(ordinal = 0, argsOnly = true) LocalFloatRef f
    ) {
        var event = new ClientFovEvent(f.get(), g.get());

        EventManager.fire(event);

        f.set(event.fovInOptions());
        g.set(event.scale());
    }
}
