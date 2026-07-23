package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Fluid.class)
public interface IFluid {
    @Invoker("isRandomlyTicking")
    boolean avoid$isRandomlyTicking();

    @Invoker("entityInside")
    void avoid$entityInside(
            net.minecraft.world.level.Level level,
            net.minecraft.core.BlockPos blockPos,
            net.minecraft.world.entity.Entity entity,
            net.minecraft.world.entity.InsideBlockEffectApplier insideBlockEffectApplier
    );

    @Invoker("getExplosionResistance")
    float avoid$getExplosionResistance();

    @Invoker("canBeReplacedWith")
    boolean avoid$canBeReplacedWith(
            net.minecraft.world.level.material.FluidState fluidState,
            net.minecraft.world.level.BlockGetter blockGetter,
            net.minecraft.core.BlockPos blockPos,
            Fluid fluid,
            net.minecraft.core.Direction direction
    );

    @Invoker("animateTick")
    void avoid$animateTick(
            net.minecraft.world.level.Level level,
            net.minecraft.core.BlockPos blockPos,
            net.minecraft.world.level.material.FluidState fluidState,
            net.minecraft.util.RandomSource randomSource
    );

    @Invoker("tick")
    void avoid$tick(
            net.minecraft.server.level.ServerLevel serverLevel,
            net.minecraft.core.BlockPos blockPos,
            net.minecraft.world.level.block.state.BlockState blockState,
            net.minecraft.world.level.material.FluidState fluidState
    );

    @Invoker("randomTick")
    void avoid$randomTick(
            net.minecraft.server.level.ServerLevel serverLevel,
            net.minecraft.core.BlockPos blockPos,
            net.minecraft.world.level.material.FluidState fluidState,
            net.minecraft.util.RandomSource randomSource
    );
}
