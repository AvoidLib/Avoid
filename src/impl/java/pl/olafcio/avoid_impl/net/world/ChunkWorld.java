package pl.olafcio.avoid_impl.net.world;

import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Untested;
import pl.olafcio.avoid.annotations.env.ClientOnly;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.errors.ImproperEnvironment;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.client.Client;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.sound.category.SoundCategory;
import pl.olafcio.avoid.net.world.Highlight;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid_impl.client.AvoidLibClient;
import pl.olafcio.avoid_impl.internal.VResourceKey;
import pl.olafcio.avoid_impl.mixininterface.IBlockOutlineRenderState;
import pl.olafcio.avoid_impl.net.block.pos.BlockPosNative;
import pl.olafcio.avoid_impl.net.entity.EntityNative;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid_impl.net.world.block_data.BlockDataNative;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ChunkWorld extends pl.olafcio.avoid.net.world.World {
    final BlockGetter level;

    ChunkWorld(BlockGetter level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ChunkWorld world && world.level == this.level;
    }

    @ApiStatus.Experimental
    @Override
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
    @Override
    public BlockData getBlock(BlockPos pos) {
        return BlockDataNative.convertFrom(level.getBlockState(BlockPosNative.convertFrom(pos)));
    }

    @NeverRemoval
    @Override
    public Entity getEntity(int id) {
        throw new RuntimeException("[World#getEntity] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public Entity getEntity(UUID uuid) {
        throw new RuntimeException("[World#getEntity] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public void eachEntity(Consumer<Entity> callback) {
        throw new RuntimeException("[World#eachEntity] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public boolean findEntity(Predicate<Entity> predicate) {
        throw new RuntimeException("[World#findEntity] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public Identification getID() {
        throw new RuntimeException("[World#getID] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public Identification getTypeID() {
        throw new RuntimeException("[World#getTypeID] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @Untested
    @Override
    public boolean autoSave() {
        throw new RuntimeException("[World#autoSave] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @NeverRemoval
    @Override
    public boolean isOverworld() {
        throw new RuntimeException("[World#isOverworld] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @NeverRemoval
    @Override
    public boolean isNether() {
        throw new RuntimeException("[World#isNether] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @NeverRemoval
    @Override
    public boolean isEnd() {
        throw new RuntimeException("[World#isEnd] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @NeverRemoval
    @Override
    public boolean isVanilla() {
        return isOverworld() || isNether() || isEnd();
    }

    @NeverRemoval
    @Override
    public Identification getBiomeAt(BlockPos pos) {
        throw new RuntimeException("[World#getBiomeAt] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @NeverRemoval
    @Override
    public long getDayTime() {
        throw new RuntimeException("[World#getDayTime] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @NeverRemoval
    @Override
    public long getGameTime() {
        throw new RuntimeException("[World#getGameTime] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public int getMinY() {
        return level.getMinY();
    }

    @ApiStatus.Experimental
    @Override
    public int getMaxY() {
        return level.getMaxY();
    }

    @Override
    public int getHeight() {
        return level.getHeight();
    }

    @ServerOnly
    @NeverRemoval
    @Override
    public void wakeUpAllPlayers() {
        throw new RuntimeException("[World#wakeUpAllPlayers] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @Override
    public boolean canHaveWeather() {
        throw new RuntimeException("[World#canHaveWeather] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @Override
    public boolean areFeaturesEnabled(Identification... id) {
        throw new RuntimeException("[World#areFeaturesEnabled] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @Override
    public void fizz(BlockPos blockPos) {
        throw new RuntimeException("[World#fizz] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @Override
    public void drop(BlockPos blockPosT) {
        var blockPos = BlockPosNative.convertFrom(blockPosT);

        var blockState = level.getBlockState(blockPos);
        var blockEntity = blockState.hasBlockEntity() ? this.level.getBlockEntity(blockPos) : null;

        if (this.level instanceof LevelAccessor levelAccessor)
            Block.dropResources(blockState, levelAccessor, blockPos, blockEntity);
        else
            throw new RuntimeException("[World#drop] This operation is not available on non-accessor chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ServerOnly
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getGameRule(Identification id) {
        if (this.level instanceof ServerLevel cast)
            return cast.getGameRules().get((GameRule<T>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)));
        else
            throw new ImproperEnvironment("[World#getGameRule] This method can only be ran on server worlds!");
    }

    @ServerOnly
    @Override
    public String getGameRuleAsString(Identification id) {
        if (this.level instanceof ServerLevel cast)
            return cast.getGameRules().getAsString((GameRule<?>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)));
        else
            throw new ImproperEnvironment("[World#getGameRuleAsString] This method can only be ran on server worlds!");
    }

    @ServerOnly
    @SuppressWarnings("unchecked")
    @Override
    public void setGameRule(Identification id, int value) {
        if (this.level instanceof ServerLevel cast)
            cast.getGameRules().set((GameRule<Integer>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)), value, AvoidInternal.getServer());
        else
            throw new ImproperEnvironment("[World#setGameRule] This method can only be ran on server worlds!");
    }

    @ServerOnly
    @SuppressWarnings("unchecked")
    @Override
    public void setGameRule(Identification id, boolean value) {
        if (this.level instanceof ServerLevel cast)
            cast.getGameRules().set((GameRule<Boolean>) BuiltInRegistries.GAME_RULE.getValue(IdentificationNative.convert(id)), value, AvoidInternal.getServer());
        else
            throw new ImproperEnvironment("[World#setGameRule] This method can only be ran on server worlds!");
    }

    @ApiStatus.Experimental
    @Override
    public void playSeededSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, float volume, float pitch, long seed) {
        throw new RuntimeException("[World#playSeededSound] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public void playSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, float volume, float pitch) {
        throw new RuntimeException("[World#playSound] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public void playSeededSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category, long seed) {
        this.playSeededSound(entity, x, y, z, soundID, category, 1f, 1f, seed);
    }

    @ApiStatus.Experimental
    @Override
    public void playSound(Entity entity, double x, double y, double z, Identification soundID, SoundCategory category) {
        this.playSound(entity, x, y, z, soundID, category, 1f, 1f);
    }

    @ApiStatus.Experimental
    @Override
    public void playSeededSound(Entity entity, Identification soundID, SoundCategory category, long seed) {
        this.playSeededSound(entity, entity.x(), entity.y(), entity.z(), soundID, category, 1f, 1f, seed);
    }

    @ApiStatus.Experimental
    @Override
    public void playSound(Entity entity, Identification soundID, SoundCategory category) {
        this.playSound(entity, entity.x(), entity.y(), entity.z(), soundID, category, 1f, 1f);
    }

    @ApiStatus.Experimental
    @Override
    public void spawnParticle(Identification particleID, double x, double y, double z, double r, double g, double b) {
        throw new RuntimeException("[World#spawnParticle] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public void spawnParticle(Identification particleID, double x, double y, double z, double r, double g, double b, boolean force, boolean canUpgradeFromMinimal) {
        throw new RuntimeException("[World#spawnParticle] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public void spawnParticle(Identification particleID, IVect3 xyz, double r, double g, double b) {
        this.spawnParticle(particleID, xyz.x(), xyz.y(), xyz.z(), r, g, b);
    }

    @ApiStatus.Experimental
    @Override
    public void spawnParticle(Identification particleID, IVect3 xyz, double r, double g, double b, boolean force, boolean canUpgradeFromMinimal) {
        this.spawnParticle(particleID, xyz.x(), xyz.y(), xyz.z(), r, g, b, force, canUpgradeFromMinimal);
    }

    @ClientOnly
    @ApiStatus.Experimental
    @Override
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
    @Override
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

    @ApiStatus.Experimental
    @Override
    public boolean isDebug() {
        throw new RuntimeException("[World#isDebug] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public boolean isRaining() {
        throw new RuntimeException("[World#isRaining] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public boolean isStorm() {
        throw new RuntimeException("[World#isStorm] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public float getRainLevel(float tickDelta) {
        throw new RuntimeException("[World#getRainLevel] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }

    @ApiStatus.Experimental
    @Override
    public float getThunderLevel(float tickDelta) {
        throw new RuntimeException("[World#getThunderLevel] This operation is not available on chunked worlds (some World objects you get don't have some capabilities)");
    }
}
