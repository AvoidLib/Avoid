package pl.olafcio.avoid.net.effect.internal;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Range;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.net.effect.Effect;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity.values.Damage;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@ApiStatus.Internal
public final class NativeEffect extends Effect {
    private final MobEffect object;

    public NativeEffect(MobEffect object) {
        this.object = object;
    }

    @Override
    public boolean tryApply(World world, Entity entity, @Range(from = 1, to = 256) int amplifier) {
        var mcLevel = WorldNative.convert(world);
        if (!(mcLevel instanceof ServerLevel))
            throw new ImproperEnvironment("[NativeEffect#tryApply] A non-server world argument has been provided");

        var mcEntity = EntityNative.convert(entity);
        if (!(mcEntity instanceof LivingEntity))
            throw new ImproperEnvironment("[NativeEffect#tryApply] A non-living entity argument has been provided");

        return object.applyEffectTick(
                (ServerLevel) mcLevel,
                (LivingEntity) mcEntity,
                amplifier - 1
        );
    }

    @Override
    public boolean shouldApply(int duration, @Range(from = 1, to = 256) int amplifier) {
        return object.shouldApplyEffectTickThisTick(duration, amplifier - 1);
    }

    @Override
    public void onAction(Entity entity, @Range(from = 1, to = 256) int amplifier) {
        var mcEntity = EntityNative.convert(entity);
        if (!(mcEntity instanceof LivingEntity))
            throw new ImproperEnvironment("[NativeEffect#onAction] A non-living entity argument has been provided");

        object.onEffectStarted(
                (LivingEntity) mcEntity,
                amplifier - 1
        );
    }

    @Override
    public void onDamage(World world, Entity entity, int amplifier, Damage damage, float tickDelta) {
        var mcWorld = WorldNative.convert(world);
        if (!(mcWorld instanceof ServerLevel))
            throw new ImproperEnvironment("[NativeEffect#onMobHurt] A non-server world argument has been provided");

        var mcEntity = EntityNative.convert(entity);
        if (!(mcEntity instanceof LivingEntity))
            throw new ImproperEnvironment("[NativeEffect#onMobHurt] A non-living entity argument has been provided");

        object.onMobHurt(
                (ServerLevel) mcWorld,
                (LivingEntity) mcEntity,
                amplifier - 1,
                new DamageSource(
                        AvoidInternal.getServer().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                                                  .getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, IdentificationNative.convert(damage.source()))),
                        damage.directEntity() == null ? null : EntityNative.convert(damage.directEntity()),
                        damage.causingEntity() == null ? null : EntityNative.convert(damage.causingEntity()),
                        damage.damageSourcePosition() == null ? null : Vect3Native.convertFrom(damage.damageSourcePosition())
                ),
                tickDelta
        );
    }
}
