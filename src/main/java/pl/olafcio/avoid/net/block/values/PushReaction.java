package pl.olafcio.avoid.net.block.values;

public enum PushReaction {
    NORMAL(net.minecraft.world.level.material.PushReaction.NORMAL),
    DESTROY(net.minecraft.world.level.material.PushReaction.DESTROY),
    BLOCK(net.minecraft.world.level.material.PushReaction.BLOCK),
    IGNORE(net.minecraft.world.level.material.PushReaction.IGNORE),
    PUSH_ONLY(net.minecraft.world.level.material.PushReaction.PUSH_ONLY);

    final net.minecraft.world.level.material.PushReaction object;

    PushReaction(net.minecraft.world.level.material.PushReaction object) {
        this.object = object;
    }
}
