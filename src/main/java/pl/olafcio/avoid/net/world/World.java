package pl.olafcio.avoid.net.world;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Untested;
import pl.olafcio.avoid.annotations.env.ClientOnly;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.sound.category.SoundCategory;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

@ApiStatus.NonExtendable
@NeverRemoval
public abstract class World {
    @Override
    public abstract boolean equals(Object obj);

    @ApiStatus.Experimental
    public abstract Identification getBlockID(BlockPos pos);

    @NeverRemoval
    public abstract BlockData getBlock(BlockPos pos);

    @NeverRemoval
    public abstract Entity getEntity(int id);

    @ApiStatus.Experimental
    public abstract Entity getEntity(UUID uuid);

    @ApiStatus.Experimental
    public abstract void eachEntity(Consumer<Entity> callback);

    @ApiStatus.Experimental
    public abstract boolean findEntity(Predicate<Entity> predicate);

    @ApiStatus.Experimental
    public abstract Identification getID();

    @ApiStatus.Experimental
    public abstract Identification getTypeID();

    @Untested
    public abstract boolean autoSave();

    @NeverRemoval
    public abstract boolean isOverworld();

    @NeverRemoval
    public abstract boolean isNether();

    @NeverRemoval
    public abstract boolean isEnd();

    @NeverRemoval
    public abstract boolean isVanilla();

    @NeverRemoval
    public abstract Identification getBiomeAt(BlockPos pos);

    @NeverRemoval
    public abstract long getDayTime();

    @NeverRemoval
    public abstract long getGameTime();

    @ApiStatus.Experimental
    public abstract int getMinY();

    @ApiStatus.Experimental
    public abstract int getMaxY();

    public abstract int getHeight();

    /**
     * Wakes up all players currently sleeping in the world.
     * <br/><br/>
     * This method works only on the server.
     */
    @ServerOnly
    @NeverRemoval
    public abstract void wakeUpAllPlayers();

    public abstract boolean canHaveWeather();

    public abstract boolean areFeaturesEnabled(Identification... id);

    public abstract void fizz(BlockPos blockPos);

    public abstract void drop(BlockPos blockPosT);

    @ServerOnly
    public abstract <T> T getGameRule(Identification id);

    @ServerOnly
    public abstract String getGameRuleAsString(Identification id);

    @ServerOnly
    public abstract void setGameRule(Identification id, int value);

    @ServerOnly
    public abstract void setGameRule(Identification id, boolean value);

    @ApiStatus.Experimental
    public abstract void playSeededSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, float volume, float pitch, long seed);

    @ApiStatus.Experimental
    public abstract void playSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, float volume, float pitch);

    @ApiStatus.Experimental
    public abstract void playSeededSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, long seed);

    @ApiStatus.Experimental
    public abstract void playSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category);

    @ApiStatus.Experimental
    public abstract void playSeededSound(Entity entity, Identification soundID, SoundCategory category, long seed);

    @ApiStatus.Experimental
    public abstract void playSound(Entity entity, Identification soundID, SoundCategory category);

    @ApiStatus.Experimental
    public abstract void spawnParticle(Identification particleID, double x, double y, double z, double r, double g, double b);

    @ApiStatus.Experimental
    public abstract void spawnParticle(Identification particleID, double x, double y, double z, double r, double g, double b, boolean force, boolean canUpgradeFromMinimal);

    @ApiStatus.Experimental
    public abstract void spawnParticle(Identification particleID, IVect3 xyz, double r, double g, double b);

    @ApiStatus.Experimental
    public abstract void spawnParticle(Identification particleID, IVect3 xyz, double r, double g, double b, boolean force, boolean canUpgradeFromMinimal);

    @ClientOnly
    @ApiStatus.Experimental
    public abstract void highlight(BlockPos blockPos, Highlight highlight);

    @ClientOnly
    @ApiStatus.Experimental
    public abstract void unhighlight(BlockPos blockPos);

    /**
     * Returns whether the world is a debug world.
     */
    @ApiStatus.Experimental
    public abstract boolean isDebug();

    /**
     * Returns whether it is currently raining in the world.
     */
    @ApiStatus.Experimental
    public abstract boolean isRaining();

    /**
     * Returns whether it is currently thunder in the world.
     */
    @ApiStatus.Experimental
    public abstract boolean isStorm();

    @ApiStatus.Experimental
    public abstract float getRainLevel(float tickDelta);

    @ApiStatus.Experimental
    public abstract float getThunderLevel(float tickDelta);
}
