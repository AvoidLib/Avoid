package pl.olafcio.avoid.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerMobClearTargetEvent;
import pl.olafcio.avoid.net.entity_server.event.ServerMobSetTargetEvent;

@Mixin(Mob.class)
public class MobMixin {
    @Inject(at = @At("HEAD"), method = "setTarget", cancellable = true)
    public void setTarget(LivingEntity livingEntity, CallbackInfo ci) {
        Cancellable event;

        if (livingEntity == null) {
            event = new ServerMobClearTargetEvent(
                    EntityNative.convertFrom((Entity) (Object) this)
            );
        } else {
            event = new ServerMobSetTargetEvent(
                    EntityNative.convertFromTry(livingEntity),
                    EntityNative.convertFrom((Entity) (Object) this)
            );
        }

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
