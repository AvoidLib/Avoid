package pl.olafcio.avoid.net.block.values;

import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class PushReactionNative {
    @ApiStatus.Internal
    private PushReactionNative() {}

    public static PushReaction convert(pl.olafcio.avoid.net.block.values.PushReaction avoid) {
        return avoid.object;
    }
}
