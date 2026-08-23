package pl.olafcio.avoid.net.effect.internal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Range;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.net.effect.Effect;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;

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
}
