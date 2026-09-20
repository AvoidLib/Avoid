package pl.olafcio.avoid.net.item.values;

import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@ApiStatus.Internal
@Native
public final class UseStatusNative {
    @ApiStatus.Internal
    private UseStatusNative() {}

    public static UseStatus convert(InteractionResult ir) {
             if (ir == InteractionResult.PASS)                return UseStatus.PASS;
        else if (ir == InteractionResult.FAIL)                return UseStatus.FAIL;
        else if (ir == InteractionResult.CONSUME)             return UseStatus.CONSUME;
        else if (ir == InteractionResult.SUCCESS)             return UseStatus.SUCCESS;
        else if (ir == InteractionResult.SUCCESS_SERVER)      return UseStatus.SUCCESS_SERVER;
        else if (ir == InteractionResult.TRY_WITH_EMPTY_HAND) return UseStatus.AIR;
        else
            throw new RuntimeException("Unexpected Minecraft use-status '%s'".formatted(ir));
    }

    public static InteractionResult convert(UseStatus ir) {
             if (ir == UseStatus.PASS)           return InteractionResult.PASS;
        else if (ir == UseStatus.FAIL)           return InteractionResult.FAIL;
        else if (ir == UseStatus.CONSUME)        return InteractionResult.CONSUME;
        else if (ir == UseStatus.SUCCESS)        return InteractionResult.SUCCESS;
        else if (ir == UseStatus.SUCCESS_SERVER) return InteractionResult.SUCCESS_SERVER;
        else if (ir == UseStatus.AIR)            return InteractionResult.TRY_WITH_EMPTY_HAND;
        else
            throw new RuntimeException("Unexpected Avoid use-status '%s'".formatted(ir));
    }
}
