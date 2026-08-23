package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityStruckByBoltEvent;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;thunderHit(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LightningBolt;)V"), method = "tick")
    public void tick__thunderHit(Entity instance, ServerLevel serverLevel, LightningBolt lightningBolt, Operation<Void> original) {
        var event = new ServerEntityStruckByBoltEvent(
                EntityNative.convertFrom(instance),
                EntityNative.convertFrom(lightningBolt)
        );

        EventManager.fire(event);

        if (!event.isCancelled())
            original.call(instance, serverLevel, lightningBolt);
    }
}
