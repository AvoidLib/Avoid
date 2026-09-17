package net.minecraft.client.renderer.state;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;

public record BlockOutlineRenderState(BlockPos pos, boolean isTranslucent, boolean highContrast, VoxelShape voxelShape, VoxelShape collision, VoxelShape occlusion, VoxelShape interaction) {
    public BlockOutlineRenderState(BlockPos pos, boolean isTranslucent, boolean highContrast, VoxelShape voxelShape) {
        this(pos, isTranslucent, highContrast, voxelShape, null, null, null);
    }
}
