package pl.olafcio.avoid.net.block.values;

public enum PushReaction {
    NORMAL(net.minecraft.world.level.material.PushReaction.PUSH_PULL),
    DESTROY(net.minecraft.world.level.material.PushReaction.POPPED),
    BLOCK(net.minecraft.world.level.material.PushReaction.IMMOVEABLE),
    IGNORE(net.minecraft.world.level.material.PushReaction.IGNORE_ENTITY),
    PUSH_ONLY(net.minecraft.world.level.material.PushReaction.PUSH);

    final net.minecraft.world.level.material.PushReaction object;

    PushReaction(net.minecraft.world.level.material.PushReaction object) {
        this.object = object;
    }
}
