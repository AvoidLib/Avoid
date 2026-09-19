package pl.olafcio.avoid.net.world;

import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.annotations.Untested;
import pl.olafcio.avoid.annotations.env.ClientOnly;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.client.AvoidLibClient;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.mixin.accessors.ILevel;
import pl.olafcio.avoid.mixininterface.IBlockOutlineRenderState;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.client.Client;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.sound.category.SoundCategory;
import pl.olafcio.avoid.net.sound.category.SoundCategoryNative;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("ClassCanBeRecord")
@NeverRemoval
public final class World {
    final Level level;

    World(Level level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof World world && world.level == this.level;
    }

    @ApiStatus.Experimental
    public Identification getBlockID(BlockPos pos) {
        return IdentificationNative.convertFrom(
                VResourceKey.identifier(
                        level.getBlockState(BlockPosNative.convertFrom(pos))
                             .getBlockHolder()
                             .unwrapKey()
                             .orElseThrow()
                )
        );
    }

    @NeverRemoval
    public BlockData getBlock(BlockPos pos) {
        return BlockDataNative.convertFrom(level.getBlockState(BlockPosNative.convertFrom(pos)));
    }

    @NeverRemoval
    public Entity getEntity(int id) {
        return EntityNative.convertFrom(level.getEntity(id));
    }

    @ApiStatus.Experimental
    public Entity getEntity(UUID uuid) {
        return EntityNative.convertFrom(level.getEntity(uuid));
    }

    @ApiStatus.Experimental
    public void eachEntity(Consumer<Entity> callback) {
        var iter = ((ILevel) level).avoid$getEntities().getAll();

        for (var e : iter)
            //noinspection ConstantValue
            if (e != null)
                callback.accept(EntityNative.convertFrom(e));
    }

    @ApiStatus.Experimental
    public boolean findEntity(Predicate<Entity> predicate) {
        var iter = ((ILevel) level).avoid$getEntities().getAll();

        for (var e : iter)
            //noinspection ConstantValue
            if (e != null)
                if (predicate.test(EntityNative.convertFrom(e)))
                    return true;

        return false;
    }

    @ApiStatus.Experimental
    public Identification getID() {
        return IdentificationNative.convertFrom(VResourceKey.identifier(level.dimension()));
    }

    @ApiStatus.Experimental
    public Identification getTypeID() {
        return IdentificationNative.convertFrom(VResourceKey.identifier(level.dimensionTypeRegistration().unwrapKey().orElseThrow()));
    }

    @Untested
    public boolean autoSave() {
        return !level.noSave();
    }

    @NeverRemoval
    public boolean isOverworld() {
        return level.dimensionTypeRegistration().is(BuiltinDimensionTypes.OVERWORLD);
    }

    @NeverRemoval
    public boolean isNether() {
        return level.dimensionTypeRegistration().is(BuiltinDimensionTypes.NETHER);
    }

    @NeverRemoval
    public boolean isEnd() {
        return level.dimensionTypeRegistration().is(BuiltinDimensionTypes.END);
    }

    @NeverRemoval
    public boolean isVanilla() {
        return isOverworld() || isNether() || isEnd();
    }

    @NeverRemoval
    public Identification getBiomeAt(BlockPos pos) {
        return IdentificationNative.convertFrom(
                VResourceKey.identifier(
                    level.getBiomeManager()
                         .getBiome(BlockPosNative.convertFrom(pos))
                         .unwrapKey()
                         .orElseThrow()
                )
        );
    }

    @NeverRemoval
    public long getDayTime() {
        return level.getDayTime();
    }

    @NeverRemoval
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
    @NeverRemoval
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

    public void fizz(BlockPos blockPos) {
        level.levelEvent(1501, BlockPosNative.convertFrom(blockPos), 0);
    }

    public void drop(BlockPos blockPosT) {
        var blockPos = BlockPosNative.convertFrom(blockPosT);

        var blockState = level.getBlockState(blockPos);
        var blockEntity = blockState.hasBlockEntity() ? this.level.getBlockEntity(blockPos) : null;

        Block.dropResources(blockState, this.level, blockPos, blockEntity);
    }

    @ServerOnly
    @SuppressWarnings("unchecked")
    public <T> T getGameRule(Identification id) {
        if (this.level instanceof ServerLevel cast)
            return cast.getGameRules().get((GameRule<T>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)));
        else
            throw new ImproperEnvironment("[World#getGameRule] This method can only be ran on server worlds!");
    }

    @ServerOnly
    public String getGameRuleAsString(Identification id) {
        if (this.level instanceof ServerLevel cast)
            return cast.getGameRules().getAsString((GameRule<?>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)));
        else
            throw new ImproperEnvironment("[World#getGameRuleAsString] This method can only be ran on server worlds!");
    }

    @ServerOnly
    @SuppressWarnings("unchecked")
    public void setGameRule(Identification id, int value) {
        if (this.level instanceof ServerLevel cast)
            cast.getGameRules().set((GameRule<Integer>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)), value, AvoidInternal.getServer());
        else
            throw new ImproperEnvironment("[World#setGameRule] This method can only be ran on server worlds!");
    }

    @ServerOnly
    @SuppressWarnings("unchecked")
    public void setGameRule(Identification id, boolean value) {
        if (this.level instanceof ServerLevel cast)
            cast.getGameRules().set((GameRule<Boolean>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)), value, AvoidInternal.getServer());
        else
            throw new ImproperEnvironment("[World#setGameRule] This method can only be ran on server worlds!");
    }

    @ApiStatus.Experimental
    public void playSeededSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, float volume, float pitch, long seed) {
        this.level.playSeededSound(EntityNative.convert(entity), x, y, z, BuiltInRegistries.SOUND_EVENT.get(IdentificationNative.convert(soundID)).orElseThrow(), SoundCategoryNative.convert(category), volume, pitch, seed);
    }

    @ApiStatus.Experimental
    public void playSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, float volume, float pitch) {
        this.level.playSound(EntityNative.convert(entity), x, y, z, BuiltInRegistries.SOUND_EVENT.get(IdentificationNative.convert(soundID)).orElseThrow(), SoundCategoryNative.convert(category), volume, pitch);
    }

    @ApiStatus.Experimental
    public void playSeededSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, long seed) {
        this.playSeededSound(entity, x, y, z, soundID, category, 1f, 1f, seed);
    }

    @ApiStatus.Experimental
    public void playSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category) {
        this.playSound(entity, x, y, z, soundID, category, 1f, 1f);
    }

    @ApiStatus.Experimental
    public void playSeededSound(Entity entity, Identification soundID, SoundCategory category, long seed) {
        this.playSeededSound(entity, entity.x(), entity.y(), entity.z(), soundID, category, 1f, 1f, seed);
    }

    @ApiStatus.Experimental
    public void playSound(Entity entity, Identification soundID, SoundCategory category) {
        this.playSound(entity, entity.x(), entity.y(), entity.z(), soundID, category, 1f, 1f);
    }

    @ApiStatus.Experimental
    public void spawnParticle(Identification particleID, double x, double y, double z, double r, double g, double b) {
        this.level.addParticle((ParticleOptions) BuiltInRegistries.PARTICLE_TYPE.getValue(IdentificationNative.convert(particleID)), x, y, z, r, g, b);
    }

    @ApiStatus.Experimental
    public void spawnParticle(Identification particleID, double x, double y, double z, double r, double g, double b, boolean force, boolean canUpgradeFromMinimal) {
        this.level.addParticle((ParticleOptions) BuiltInRegistries.PARTICLE_TYPE.getValue(IdentificationNative.convert(particleID)), force, canUpgradeFromMinimal, x, y, z, r, g, b);
    }

    @ApiStatus.Experimental
    public void spawnParticle(Identification particleID, IVect3 xyz, double r, double g, double b) {
        this.spawnParticle(particleID, xyz.x(), xyz.y(), xyz.z(), r, g, b);
    }

    @ApiStatus.Experimental
    public void spawnParticle(Identification particleID, IVect3 xyz, double r, double g, double b, boolean force, boolean canUpgradeFromMinimal) {
        this.spawnParticle(particleID, xyz.x(), xyz.y(), xyz.z(), r, g, b, force, canUpgradeFromMinimal);
    }

    @ClientOnly
    @ApiStatus.Experimental
    public void highlight(BlockPos blockPos, Highlight highlight) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("[World#highlight] This method can only be ran on client worlds!");

        final var v_blockPos = BlockPosNative.convertFrom(blockPos);
        final var v_blockState = this.level.getBlockState(v_blockPos);

        boolean translucent = ItemBlockRenderTypes.getChunkRenderType(v_blockState).sortOnUpload();
        boolean highContrast = AvoidLibClient.mc.options.highContrastBlockOutline().get();

        final var collisionContext = CollisionContext.of(
                                         EntityNative.convert(
                                                 Objects.requireNonNull(Client.getCamera(), "Camera not initialized (player probably isn't fully loaded into the world)")
                                         )
                                     );

        final var voxelShape = v_blockState.getShape(this.level, v_blockPos, collisionContext);

        BlockOutlineRenderState renderState;

        //TODO Wtf is this
        if (SharedConstants.DEBUG_SHAPES) {
            VoxelShape collision = v_blockState.getCollisionShape(this.level, v_blockPos, collisionContext);
            VoxelShape occlusion = v_blockState.getOcclusionShape();
            VoxelShape interaction = v_blockState.getInteractionShape(this.level, v_blockPos);

            WorldNative.highlights.add(renderState = new BlockOutlineRenderState(v_blockPos, translucent, highContrast, voxelShape, collision, occlusion, interaction));
        } else {
            WorldNative.highlights.add(renderState = new BlockOutlineRenderState(v_blockPos, translucent, highContrast, voxelShape));
        }

        var cast = (IBlockOutlineRenderState) (Object) renderState;

        cast.color         (highlight.color());
        cast.secondaryColor(highlight.secondaryColor());

        cast.         color_highcontrast(highlight.color_highcontrast());
        cast.secondaryColor_highcontrast(highlight.secondaryColor_highcontrast());

        cast.lineWidth          (highlight.lineWidth());
        cast.lineWidth_secondary(highlight.lineWidth_secondary());
    }

    @ClientOnly
    @ApiStatus.Experimental
    public void unhighlight(BlockPos blockPos) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("[World#highlight] This method can only be ran on client worlds!");

        WorldNative.highlights.removeIf(renderState -> {
            var pos = renderState.pos();

            return pos.getX() == blockPos.x() &&
                   pos.getY() == blockPos.y() &&
                   pos.getZ() == blockPos.z();
        });
    }

    /**
     * Returns whether the world is a debug world.
     */
    @ApiStatus.Experimental
    public boolean isDebug() {
        return level.isDebug();
    }

    /**
     * Returns whether it is currently raining in the world.
     */
    @ApiStatus.Experimental
    public boolean isRaining() {
        return level.isRaining();
    }

    /**
     * Returns whether it is currently thunder in the world.
     */
    @ApiStatus.Experimental
    public boolean isStorm() {
        return level.isThundering();
    }

    @ApiStatus.Experimental
    public float getRainLevel(float tickDelta) {
        return level.getRainLevel(tickDelta);
    }

    @ApiStatus.Experimental
    public float getThunderLevel(float tickDelta) {
        return level.getThunderLevel(tickDelta);
    }
}
