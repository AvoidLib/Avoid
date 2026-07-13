package pl.olafcio.avoid.net.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.annotations.Untested;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

import java.util.Arrays;
import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
@ApiStatus.Experimental
public final class World {
    final Level level;

    World(Level level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof World world && world.level == this.level;
    }

    public Identification getBlockID(BlockPos pos) {
        return IdentificationNative.convertFrom(level.getBlockState(BlockPosNative.convertFrom(pos)).getBlockHolder().unwrapKey().orElseThrow().identifier());
    }

    public BlockData getBlock(BlockPos pos) {
        return BlockDataNative.convertFrom(level.getBlockState(BlockPosNative.convertFrom(pos)));
    }

    public Entity getEntity(int id) {
        return EntityNative.convertFrom(level.getEntity(id));
    }

    @ApiStatus.Experimental
    public Entity getEntity(UUID uuid) {
        return EntityNative.convertFrom(level.getEntity(uuid));
    }

    public Identification getID() {
        return IdentificationNative.convertFrom(level.dimension().identifier());
    }

    public Identification getTypeID() {
        return IdentificationNative.convertFrom(level.dimensionTypeRegistration().unwrapKey().orElseThrow().identifier());
    }

    @Untested
    public boolean autoSave() {
        return !level.noSave();
    }

    public boolean isOverworld() {
        return level.dimensionTypeRegistration().is(BuiltinDimensionTypes.OVERWORLD);
    }

    public boolean isNether() {
        return level.dimensionTypeRegistration().is(BuiltinDimensionTypes.NETHER);
    }

    public boolean isEnd() {
        return level.dimensionTypeRegistration().is(BuiltinDimensionTypes.END);
    }

    public boolean isVanilla() {
        return isOverworld() || isNether() || isEnd();
    }

    public Identification getBiomeAt(BlockPos pos) {
        return IdentificationNative.convertFrom(level.getBiomeManager().getBiome(BlockPosNative.convertFrom(pos)).unwrapKey().orElseThrow().identifier());
    }

    public long getDayTime() {
        return level.getDayTime();
    }

    public long getGameTime() {
        return level.getGameTime();
    }

    @ApiStatus.Experimental
    public int getMinY() {
        return level.getMinY();
    }

    @ApiStatus.Experimental
    public int getMaxY() {
        return level.getMaxY();
    }

    public int getHeight() {
        return level.getHeight();
    }

    /**
     * Wakes up all players currently sleeping in the world.
     * <br/><br/>
     * This method works only on the server.
     */
    @ServerOnly
    public void wakeUpAllPlayers() {
        if (this.level instanceof ServerLevel cast)
            cast.wakeUpAllPlayers();
        else
            throw new ImproperEnvironment("[World#wakeUpAllPlayers] This method can only be ran on server worlds!");
    }

    public boolean canHaveWeather() {
        return level.canHaveWeather();
    }

    public boolean areFeaturesEnabled(Identification... id) {
        return FeatureFlags.REGISTRY.fromNames(Arrays.stream(id).map(IdentificationNative::convert).toList())
                                    .isSubsetOf(level.enabledFeatures());
    }
}
