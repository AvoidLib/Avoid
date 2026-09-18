package pl.olafcio.avoid.mixin;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity.values.EnderDragonPhaseNative;
import pl.olafcio.avoid.net.entity_server.event.ender_dragon.ServerEnderDragonPhaseEvent;

@Mixin(EnderDragonPhaseManager.class)
public class EnderDragonPhaseManagerMixin {
    @Shadow
    @Final
    private EnderDragon dragon;

    @Inject(
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/boss/enderdragon/phases/EnderDragonPhaseManager;currentPhase:Lnet/minecraft/world/entity/boss/enderdragon/phases/DragonPhaseInstance;",
                    ordinal = 2,
                    opcode = Opcodes.GETFIELD
            ),
            method = "setPhase",
            cancellable = true
    )
    public void setPhase(EnderDragonPhase<?> enderDragonPhase, CallbackInfo ci) {
        var event = new ServerEnderDragonPhaseEvent(EntityNative.convertFrom(dragon), EnderDragonPhaseNative.convert(enderDragonPhase));

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
