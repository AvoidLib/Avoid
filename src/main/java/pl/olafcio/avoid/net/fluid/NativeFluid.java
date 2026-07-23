package pl.olafcio.avoid.net.fluid;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.mixin.accessors.IFlowingFluid;
import pl.olafcio.avoid.mixin.accessors.IFluid;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net.block.random.RandomProviderNative;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.fluid.inside_block.InsideBlock;
import pl.olafcio.avoid.net.fluid.inside_block.InsideBlockActionNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.ItemNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

import java.util.function.Consumer;

@NullMarked
public final class NativeFluid extends Fluid {
    net.minecraft.world.level.material.@UnknownNullability Fluid fluid;

    NativeFluid(net.minecraft.world.level.material.@UnknownNullability Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public Item getBucket() {
        return ItemNative.convert(fluid.getBucket());
    }

    @Override
    public Identification getBlockType() {
        return IdentificationNative.convertFrom(BuiltInRegistries.BLOCK.getKey(((IFluid) fluid).avoid$createLegacyBlock(new net.minecraft.world.level.material.FluidState(fluid, new Reference2ObjectArrayMap<>(), null)).getBlock()));
    }

    @Override
    public boolean animateTick(World world, BlockPos blockPos, FluidState fluidState, RandomProvider randomProvider) {
        ((IFluid) fluid).avoid$animateTick(WorldNative.convert(world), BlockPosNative.convertFrom(blockPos), fluidState.state, RandomProviderNative.convert(randomProvider));
        return OK;
    }

    @Override
    public boolean tick(World world, BlockPos blockPos, FluidState fluidState, BlockData blockData) {
        ((IFluid) fluid).avoid$tick((ServerLevel) WorldNative.convert(world), BlockPosNative.convertFrom(blockPos), BlockDataNative.convert(blockData), fluidState.state);
        return OK;
    }

    @Override
    public boolean randomTick(World world, BlockPos blockPos, FluidState fluidState, RandomProvider randomProvider) {
        ((IFluid) fluid).avoid$randomTick((ServerLevel) WorldNative.convert(world), BlockPosNative.convertFrom(blockPos), fluidState.state, RandomProviderNative.convert(randomProvider));
        return OK;
    }

    @Override
    public boolean isRandomlyTicking() {
        return ((IFluid) fluid).avoid$isRandomlyTicking();
    }

    @Override
    public void onEntityEncounter(World world, BlockPos blockPos, Entity entity, InsideBlock insideBlock) {
        ((IFluid) fluid).avoid$entityInside(WorldNative.convert(world), BlockPosNative.convertFrom(blockPos), EntityNative.convert(entity), new InsideBlockEffectApplier() {
            @Override
            public void apply(InsideBlockEffectType insideBlockEffectType) {
                insideBlock.schedule(InsideBlockActionNative.convertFrom(insideBlockEffectType));
            }

            @Override
            public void runBefore(InsideBlockEffectType insideBlockEffectType, Consumer<net.minecraft.world.entity.Entity> consumer) {
                insideBlock.scheduleBefore(InsideBlockActionNative.convertFrom(insideBlockEffectType), entity -> consumer.accept(EntityNative.convert(entity)));
            }

            @Override
            public void runAfter(InsideBlockEffectType insideBlockEffectType, Consumer<net.minecraft.world.entity.Entity> consumer) {
                insideBlock.scheduleAfter(InsideBlockActionNative.convertFrom(insideBlockEffectType), entity -> consumer.accept(EntityNative.convert(entity)));
            }
        });
    }

    @Override
    public void onBlockEncounter(World world, BlockPos blockPos, BlockData blockData) {
        if (fluid instanceof IFlowingFluid ff)
            ff.avoid$beforeDestroyingBlock(WorldNative.convert(world), BlockPosNative.convertFrom(blockPos), BlockDataNative.convert(blockData));
    }

    @Override
    protected float getExplosionResistance() {
        return ((IFluid) fluid).avoid$getExplosionResistance();
    }

    @Override
    public int getTickDelay(World world) {
        return fluid.getTickDelay(WorldNative.convert(world));
    }

    @Override
    public int getSpeed(World world) {
        if (fluid instanceof IFlowingFluid ff)
            return ff.avoid$getSlopeFindDistance(WorldNative.convert(world));

        return 0;
    }

    @Override
    public int getSpread(FluidState state) {
        return fluid.getAmount(state.state);
    }

    @Override
    public int getDropOff(World world) {
        if (fluid instanceof IFlowingFluid ff)
            return ff.avoid$getDropOff(WorldNative.convert(world));

        return 0;
    }

    @Override
    public boolean isSourceable(World world) {
        return fluid instanceof IFlowingFluid ff && ff.avoid$canConvertToSource((ServerLevel) WorldNative.convert(world));
    }

    @Override
    public boolean isReplaceable(FluidState state, World world, BlockPos blockPos, Fluid fluid, Direction direction) {
        return ((IFluid) this.fluid).avoid$canBeReplacedWith(
                state.state,
                WorldNative.convert(world),
                BlockPosNative.convertFrom(blockPos),
                fluid.fluid,
                net.minecraft.core.Direction.valueOf(direction.name())
        );
    }

    @Override
    public BlockData createBlock(FluidState state) {
        return BlockDataNative.convertFrom(((IFluid) fluid).avoid$createLegacyBlock(state.state));
    }
}
