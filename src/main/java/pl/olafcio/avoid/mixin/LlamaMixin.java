package pl.olafcio.avoid.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.llama.ServerLlamaSpitEvent;

@Mixin(Llama.class)
public class LlamaMixin {
    @Inject(at = @At("HEAD"), method = "spit", cancellable = true)
    private void spit(LivingEntity livingEntity, CallbackInfo ci) {
        var event = new ServerLlamaSpitEvent(
                EntityNative.convertFrom(livingEntity),
                EntityNative.convertFrom((Entity) (Object) this)
        );

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
