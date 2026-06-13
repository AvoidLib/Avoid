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

        if (block.isAnnotationPresent(_randomlyTicking.class))
            properties = properties.randomTicks();

        if (block.isAnnotationPresent(_instabreak.class)) {
            properties = properties.instabreak();

            if (block.isAnnotationPresent(_strength.class) || block.isAnnotationPresent(_destroyTime.class) || block.isAnnotationPresent(_explosionResistance.class)) {
                Avoid.LOGGER.warn("Block has @_instabreak, other strength-related attributes are ignored (@_strength &/ @_destroyTime &/ @_explosionResistance)");
            }
        } else {
            if (block.isAnnotationPresent(_strength.class))
                properties = properties.strength(block.getAnnotation(_strength.class)
                                       .value());

            if (block.isAnnotationPresent(_destroyTime.class))
                properties = properties.destroyTime(block.getAnnotation(_destroyTime.class)
                                       .value());

            if (block.isAnnotationPresent(_explosionResistance.class))
                properties = properties.destroyTime(block.getAnnotation(_explosionResistance.class)
                                       .value());

            if (block.isAnnotationPresent(_destroyTime.class) && block.isAnnotationPresent(_explosionResistance.class) && block.isAnnotationPresent(_strength.class))
                Avoid.LOGGER.warn("Block has @_strength, @_destroyTime and @_explosionResistance at the same time; strength definition is unnecessary");
        }

        if (block.isAnnotationPresent(_friction.class))
            properties = properties.friction(block.getAnnotation(_friction.class)
                                                  .value());

        if (block.isAnnotationPresent(_speedFactor.class))
            properties = properties.speedFactor(block.getAnnotation(_speedFactor.class)
                                                     .value());

        if (block.isAnnotationPresent(_jumpFactor.class))
            properties = properties.jumpFactor(block.getAnnotation(_jumpFactor.class)
                                                    .value());

        if (block.isAnnotationPresent(_noCollision.class))
            properties = properties.noCollision();

        if (block.isAnnotationPresent(_liquid.class))
            properties = properties.liquid();

        if (block.isAnnotationPresent(_ignitedByLava.class))
            properties = properties.ignitedByLava();

        if (block.isAnnotationPresent(_noTerrainParticles.class))
            properties = properties.noTerrainParticles();

        if (block.isAnnotationPresent(_replaceable.class))
            properties = properties.replaceable();

        if (block.isAnnotationPresent(_requiresCorrectToolForDrops.class))
            properties = properties.requiresCorrectToolForDrops();

        if (block.isAnnotationPresent(_forceSolid.class))
            properties = properties.forceSolidOn();

        if (block.isAnnotationPresent(_noDrops.class))
            properties = properties.noLootTable();

        if (block.isAnnotationPresent(_requiresCorrectToolForDrops.class) && block.isAnnotationPresent(_noDrops.class))
            Avoid.LOGGER.warn("@_requiresCorrectToolForDrops and @_noDrops present; only-tool drop declaration is unnecessary");

        if (block.isAnnotationPresent(_instrument.class))
            properties = properties.instrument(NoteBlockInstrumentNative.convert(
                    block.getAnnotation(_instrument.class)
                         .value()
            ));

        return properties.mapColor(MapColor.GRASS).sound(SoundType.GRASS);
    }
}
