package pl.olafcio.avoid_impl.net.world;

import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import pl.olafcio.avoid.errors.ImproperEnvironment;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.client.Client;
import pl.olafcio.avoid.net.world.Highlight;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid_impl.client.AvoidLibClient;
import pl.olafcio.avoid_impl.mixininterface.IBlockOutlineRenderState;
import pl.olafcio.avoid_impl.net.block.pos.BlockPosNative;
import pl.olafcio.avoid_impl.net.entity.EntityNative;

import java.util.Objects;

enum ClientStuff {
    ;

    static void highlightstatic(BlockGetter level, BlockPos blockPos, Highlight highlight) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("[World#highlight] This method can only be ran on client worlds!");

        final var v_blockPos = BlockPosNative.convertFrom(blockPos);
        final var v_blockState = level.getBlockState(v_blockPos);

        boolean translucent = ItemBlockRenderTypes.getChunkRenderType(v_blockState).sortOnUpload();
        boolean highContrast = AvoidLibClient.mc.options.highContrastBlockOutline().get();

        final var collisionContext = CollisionContext.of(
                EntityNative.convert(
                        Objects.requireNonNull(Client.getCamera(), "Camera not initialized (player probably isn't fully loaded into the world)")
                )
        );

        final var voxelShape = v_blockState.getShape(level, v_blockPos, collisionContext);

        BlockOutlineRenderState renderState;

        //TODO Wtf is this
        if (SharedConstants.DEBUG_SHAPES) {
            VoxelShape collision = v_blockState.getCollisionShape(level, v_blockPos, collisionContext);
            VoxelShape occlusion = v_blockState.getOcclusionShape();
            VoxelShape interaction = v_blockState.getInteractionShape(level, v_blockPos);

            WorldNative.highlights.add(renderState = new BlockOutlineRenderState(v_blockPos, translucent, highContrast, voxelShape, collision, occlusion, interaction));
        } else {
            WorldNative.highlights.add(renderState = new BlockOutlineRenderState(v_blockPos, translucent, highContrast, voxelShape));
        }

        var cast = (IBlockOutlineRenderState) (Object) renderState;

        cast.color(highlight.color());
        cast.secondaryColor(highlight.secondaryColor());

        cast.color_highcontrast(highlight.color_highcontrast());
        cast.secondaryColor_highcontrast(highlight.secondaryColor_highcontrast());

        cast.lineWidth(highlight.lineWidth());
        cast.lineWidth_secondary(highlight.lineWidth_secondary());
    }

    static void unhighlightstatic(BlockPos blockPos) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("[World#unhighlight] This method can only be ran on client worlds!");

        WorldNative.highlights.removeIf(renderState -> {
            var pos = renderState.pos();

            return pos.getX() == blockPos.x() &&
                   pos.getY() == blockPos.y() &&
                   pos.getZ() == blockPos.z();
        });
    }
}
