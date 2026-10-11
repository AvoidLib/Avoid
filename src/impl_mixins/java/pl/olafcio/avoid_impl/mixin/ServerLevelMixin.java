package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.net.server.event.ServerPlaySoundEvent;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid_impl.internal.VResourceKey;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid_impl.mixinclass.NativeServerSound;
import pl.olafcio.avoid_impl.net.entity.EntityNative;
import pl.olafcio.avoid_impl.net.entity.values.DamageNative;
import pl.olafcio.avoid.net.server.event.ServerExplodeEvent;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid_impl.net.world.WorldNative;
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

    @Unique
    private boolean cancelled = false;

    @WrapOperation(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;Lnet/minecraft/world/entity/Entity;FFJ)Lnet/minecraft/network/protocol/game/ClientboundSoundEntityPacket;"), method = "playSeededSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V")
    public ClientboundSoundEntityPacket init(Holder<SoundEvent> holder, SoundSource soundSource, Entity entity, float f, float g, long l, Operation<ClientboundSoundEntityPacket> original) {
        var packet = original.call(holder, soundSource, entity, f, g, l);
        var event = new ServerPlaySoundEvent(
                new NativeServerSound(holder.value(), soundSource, entity.getX(), entity.getY(), entity.getZ(), packet.getVolume(), packet.getPitch(), packet.getSeed()),
                EntityNative.convertFrom(entity)
        );

        EventManager.fire(event);

        if (event.isCancelled())
            this.cancelled = true;

        return packet;
    }

    @WrapOperation(at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;DDDFFJ)Lnet/minecraft/network/protocol/game/ClientboundSoundPacket;"), method = "playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V")
    public ClientboundSoundPacket init(Holder<SoundEvent> holder, SoundSource soundSource, double d, double e, double f, float g, float h, long l, Operation<ClientboundSoundPacket> original) {
        var packet = original.call(holder, soundSource, d, e, f, g, h, l);
        var event = new ServerPlaySoundEvent(
                new NativeServerSound(holder.value(), soundSource, packet.getX(), packet.getY(), packet.getZ(), packet.getVolume(), packet.getPitch(), packet.getSeed()),
                null
        );

        EventManager.fire(event);

        if (event.isCancelled())
            this.cancelled = true;

        return packet;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcast(Lnet/minecraft/world/entity/player/Player;DDDDLnet/minecraft/resources/ResourceKey;Lnet/minecraft/network/protocol/Packet;)V"), method = {"playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V", "playSeededSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V"}, cancellable = true)
    public void playSeededSound(CallbackInfo ci) {
        if (cancelled) {
            ci.cancel();
            cancelled = false;
        }
    }
}
