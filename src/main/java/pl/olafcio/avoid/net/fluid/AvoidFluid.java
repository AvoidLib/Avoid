package pl.olafcio.avoid.net.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.block.random.RandomProviderNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.fluid.inside_block.InsideBlock;
import pl.olafcio.avoid.net.fluid.inside_block.InsideBlockAction;
import pl.olafcio.avoid.net.fluid.inside_block.InsideBlockActionNative;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.ItemNative;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

import java.util.function.Consumer;

@ApiStatus.Internal
@NullMarked
public abstract sealed class AvoidFluid extends FlowingFluid {
    final pl.olafcio.avoid.net.fluid.Fluid fluid;

    @UnknownNullability AvoidFluid flowing;
    @UnknownNullability AvoidFluid source;

    private AvoidFluid(pl.olafcio.avoid.net.fluid.Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public Fluid getFlowing() {
        return flowing;
    }

    @Override
    public Fluid getSource() {
        return source;
    }

    @Override
    public Item getBucket() {
        return ItemNative.convert(fluid.getBucket());
    }

    @Override
    public void tick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (fluid.tick(WorldNative.make(serverLevel), BlockPosNative.convert(blockPos), new pl.olafcio.avoid.net.fluid.FluidState(fluidState), BlockDataNative.convertFrom(blockState)))
            super.tick(serverLevel, blockPos, blockState, fluidState);
    }

    @Override
    protected void animateTick(Level level, BlockPos blockPos, FluidState fluidState, RandomSource randomSource) {
        if (fluid.animateTick(WorldNative.make(level), BlockPosNative.convert(blockPos), new pl.olafcio.avoid.net.fluid.FluidState(fluidState), RandomProviderNative.create(randomSource)))
            super.animateTick(level, blockPos, fluidState, randomSource);
    }

    @Override
    protected void randomTick(ServerLevel serverLevel, BlockPos blockPos, FluidState fluidState, RandomSource randomSource) {
        if (fluid.randomTick(WorldNative.make(serverLevel), BlockPosNative.convert(blockPos), new pl.olafcio.avoid.net.fluid.FluidState(fluidState), RandomProviderNative.create(randomSource)))
            super.randomTick(serverLevel, blockPos, fluidState, randomSource);
    }

    @Override
    protected boolean isRandomlyTicking() {
        return fluid.isRandomlyTicking();
    }

    @Override
    protected void entityInside(Level level, BlockPos blockPos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier) {
        fluid.onEntityEncounter(
                WorldNative.make(level),
                BlockPosNative.convert(blockPos),
                EntityNative.convertFrom(entity),
                new InsideBlock() {
                    @Override
                    public void schedule(InsideBlockAction action) {
                        insideBlockEffectApplier.apply(InsideBlockActionNative.convert(action));
                    }

                    @Override
                    public void scheduleBefore(InsideBlockAction action, Consumer<pl.olafcio.avoid.net.entity.Entity> consumer) {
                        insideBlockEffectApplier.runBefore(
                                InsideBlockActionNative.convert(action),
                                entity -> consumer.accept(EntityNative.convertFrom(entity))
                        );
                    }

                    @Override
                    public void scheduleAfter(InsideBlockAction action, Consumer<pl.olafcio.avoid.net.entity.Entity> consumer) {
                        insideBlockEffectApplier.runAfter(
                                InsideBlockActionNative.convert(action),
                                entity -> consumer.accept(EntityNative.convertFrom(entity))
                        );
                    }
                }
        );
    }

    @Override
    protected boolean canConvertToSource(ServerLevel serverLevel) {
        return fluid.isSourceable(WorldNative.make(serverLevel));
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        fluid.onBlockEncounter(WorldNative.make((Level) levelAccessor), BlockPosNative.convert(blockPos), BlockDataNative.convertFrom(blockState));
    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return fluid.getSpeed(WorldNative.make((Level) levelReader));
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return fluid.getDropOff(WorldNative.make((Level) levelReader));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == flowing || fluid == source;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return this.fluid.isReplaceable(
                new pl.olafcio.avoid.net.fluid.FluidState(fluidState),
                WorldNative.make((Level) blockGetter),
                BlockPosNative.convert(blockPos),
                new NativeFluid(fluid),
                pl.olafcio.avoid.net._3d.Direction.valueOf(direction.name())
        );
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return fluid.getTickDelay(WorldNative.make((Level) levelReader));
    }

    @Override
    protected float getExplosionResistance() {
        return fluid.getExplosionResistance();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return BlockDataNative.convert(fluid.createBlock(new pl.olafcio.avoid.net.fluid.FluidState(fluidState)));
    }

    public static final class Flowing extends AvoidFluid {
        Flowing(pl.olafcio.avoid.net.fluid.Fluid fluid) {
            super(fluid);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }

    public static final class Source extends AvoidFluid {
        Source(pl.olafcio.avoid.net.fluid.Fluid fluid) {
            super(fluid);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluid.getSpread(new pl.olafcio.avoid.net.fluid.FluidState(fluidState));
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }
}
