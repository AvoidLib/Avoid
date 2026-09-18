package pl.olafcio.avoid.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity.values.DamageNative;
import pl.olafcio.avoid.net.entity_server.event.ender_dragon.ServerEnderDragonCrystalBrokenProcessEvent;
import pl.olafcio.avoid.net.world.WorldNative;

@Mixin(EnderDragon.class)
public class EnderDragonMixin {
    @Inject(at = @At("HEAD"), method = "onCrystalDestroyed", cancellable = true)
    public void onCrystalDestroyed(ServerLevel serverLevel, EndCrystal endCrystal, BlockPos blockPos, DamageSource damageSource, CallbackInfo ci) {
        var event = new ServerEnderDragonCrystalBrokenProcessEvent(
                WorldNative.make(serverLevel),
                EntityNative.convertFrom(endCrystal),
                BlockPosNative.convert(blockPos),
                DamageNative.convert(damageSource, AvoidInternal.getServer().registryAccess())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
