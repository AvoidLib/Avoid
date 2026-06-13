package pl.olafcio.avoid.net.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.net.block.properties.*;
import pl.olafcio.avoid.net.block.values.NoteBlockInstrumentNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.BlockBehaviour.simpleCodec;

@ApiStatus.Experimental
public final class Blocks {
    @ApiStatus.Internal
    private Blocks() {}

    public static void register(Identification blockID, Supplier<? extends pl.olafcio.avoid.net.block.Block> constructor) {
        var id = IdentificationNative.convert(blockID);

        Function<Properties, Block> callback = properties -> BlockNative.make(
                constructor.get(),
                properties
        );

        var block = net.minecraft.world.level.block.Blocks.register(
                ResourceKey.create(Registries.BLOCK, id),
                callback,
                getProperties(constructor.get().getClass())
        );

        Registry.register(BuiltInRegistries.BLOCK_TYPE, id, simpleCodec(callback));

        for (BlockState blockState : block.getStateDefinition().getPossibleStates()) {
            Block.BLOCK_STATE_REGISTRY.add(blockState);
            blockState.initCache();
        }
    }

    private static Properties getProperties(Class<? extends pl.olafcio.avoid.net.block.Block> block) {
        var properties = Properties.of();

        if (block.isAnnotationPresent(RandomlyTicking.class))
            properties = properties.randomTicks();

        if (block.isAnnotationPresent(Instabreak.class)) {
            properties = properties.instabreak();

            if (block.isAnnotationPresent(Strength.class) || block.isAnnotationPresent(DestroyTime.class) || block.isAnnotationPresent(ExplosionResistance.class)) {
                Avoid.LOGGER.warn("Block has @Instabreak, other strength-related attributes are ignored (@Strength &/ @DestroyTime &/ @ExplosionResistance)");
            }
        } else {
            if (block.isAnnotationPresent(Strength.class))
                properties = properties.strength(block.getAnnotation(Strength.class)
                                       .value());

            if (block.isAnnotationPresent(DestroyTime.class))
                properties = properties.destroyTime(block.getAnnotation(DestroyTime.class)
                                       .value());

            if (block.isAnnotationPresent(ExplosionResistance.class))
                properties = properties.destroyTime(block.getAnnotation(ExplosionResistance.class)
                                       .value());

            if (block.isAnnotationPresent(DestroyTime.class) && block.isAnnotationPresent(ExplosionResistance.class) && block.isAnnotationPresent(Strength.class))
                Avoid.LOGGER.warn("Block has @Strength, @DestroyTime and @ExplosionResistance at the same time; strength definition is unnecessary");
        }

        if (block.isAnnotationPresent(Friction.class))
            properties = properties.friction(block.getAnnotation(Friction.class)
                                                  .value());

        if (block.isAnnotationPresent(SpeedFactor.class))
            properties = properties.speedFactor(block.getAnnotation(SpeedFactor.class)
                                                     .value());

        if (block.isAnnotationPresent(JumpFactor.class))
            properties = properties.jumpFactor(block.getAnnotation(JumpFactor.class)
                                                    .value());

        if (block.isAnnotationPresent(NoCollision.class))
            properties = properties.noCollision();

        if (block.isAnnotationPresent(Liquid.class))
            properties = properties.liquid();

        if (block.isAnnotationPresent(IgnitedByLava.class))
            properties = properties.ignitedByLava();

        if (block.isAnnotationPresent(NoTerrainParticles.class))
            properties = properties.noTerrainParticles();

        if (block.isAnnotationPresent(Replaceable.class))
            properties = properties.replaceable();

        if (block.isAnnotationPresent(RequiresCorrectToolForDrops.class))
            properties = properties.requiresCorrectToolForDrops();

        if (block.isAnnotationPresent(ForceSolid.class))
            properties = properties.forceSolidOn();

        if (block.isAnnotationPresent(NoDrops.class))
            properties = properties.noLootTable();

        if (block.isAnnotationPresent(RequiresCorrectToolForDrops.class) && block.isAnnotationPresent(NoDrops.class))
            Avoid.LOGGER.warn("@RequiresCorrectToolForDrops and @NoDrops present; only-tool drop declaration is unnecessary");

        if (block.isAnnotationPresent(Instrument.class))
            properties = properties.instrument(NoteBlockInstrumentNative.convert(
                    block.getAnnotation(Instrument.class)
                         .value()
            ));

        return properties.mapColor(MapColor.GRASS).sound(SoundType.GRASS);
    }
}
