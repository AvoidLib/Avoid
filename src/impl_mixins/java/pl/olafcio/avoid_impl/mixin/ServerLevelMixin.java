package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid_impl.internal.VResourceKey;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid_impl.net.entity.EntityNative;
import pl.olafcio.avoid_impl.net.entity.values.DamageNative;
import pl.olafcio.avoid.net.server.event.ServerExplodeEvent;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid_impl.net.world.vect3.Vect3Native;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(at = @At(value = "NEW", target = "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;Lnet/minecraft/world/phys/Vec3;FZLnet/minecraft/world/level/Explosion$BlockInteraction;)Lnet/minecraft/world/level/ServerExplosion;"), method = "explode", cancellable = true)
    public void explode(
            @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator explosionDamageCalculator,
            double d, double e, double f,
            float g, boolean bl,
            Level.ExplosionInteraction explosionInteraction,
            ParticleOptions particleOptions, ParticleOptions particleOptions2,
            WeightedList<ExplosionParticleInfo> weightedList,
            Holder<SoundEvent> holder,
            CallbackInfo ci,
            @Local(ordinal = 0) Vec3 vec3,
            @Local(argsOnly = true) LocalFloatRef radius
    ) {
        var event = new ServerExplodeEvent(
                entity == null ? null : EntityNative.convertFrom(entity),
                damageSource == null ? null : DamageNative.convert(damageSource, AvoidInternal.getServer().registryAccess()),
                WorldNative.make((ServerLevel) (Object) this),
                Vect3Native.convert(vec3),
                g,
                bl,
                IdentificationNative.convertFrom(VResourceKey.identifier(holder.unwrapKey().orElseThrow()))
        );

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
        else
            radius.set(event.getRadius());
    }
}
