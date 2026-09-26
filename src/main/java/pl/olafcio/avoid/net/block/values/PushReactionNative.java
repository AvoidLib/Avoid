package pl.olafcio.avoid.net.block.values;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class PushReactionNative {
    @ApiStatus.Internal
    private PushReactionNative() {}

    public static net.minecraft.world.level.material.PushReaction convert(PushReaction avoid) {
             if (avoid == PushReaction.NORMAL)    return net.minecraft.world.level.material.PushReaction.NORMAL;
        else if (avoid == PushReaction.DESTROY)   return net.minecraft.world.level.material.PushReaction.DESTROY;
        else if (avoid == PushReaction.BLOCK)     return net.minecraft.world.level.material.PushReaction.BLOCK;
        else if (avoid == PushReaction.IGNORE)    return net.minecraft.world.level.material.PushReaction.IGNORE;
        else if (avoid == PushReaction.PUSH_ONLY) return net.minecraft.world.level.material.PushReaction.PUSH_ONLY;
        else
            throw new RuntimeException("Unknown Avoid push-reaction '%s'".formatted(avoid));
    }
}
