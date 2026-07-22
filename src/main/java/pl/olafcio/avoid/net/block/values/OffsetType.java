package pl.olafcio.avoid.net.block.values;

import net.minecraft.world.level.block.state.BlockBehaviour;

public enum OffsetType {
    NONE(BlockBehaviour.OffsetType.NONE),
    XZ(BlockBehaviour.OffsetType.XZ),
    XYZ(BlockBehaviour.OffsetType.XYZ);

    final BlockBehaviour.OffsetType object;

    OffsetType(BlockBehaviour.OffsetType object) {
        this.object = object;
    }
}
