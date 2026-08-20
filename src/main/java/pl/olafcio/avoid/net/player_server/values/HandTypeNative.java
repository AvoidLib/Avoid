package pl.olafcio.avoid.net.player_server.values;

import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class HandTypeNative {
    @ApiStatus.Internal
    private HandTypeNative() {}

    public static HandType convert(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? HandType.MAIN_HAND
                                                 : HandType.OFF_HAND;
    }

    public static InteractionHand convertFrom(HandType hand) {
        return hand == HandType.MAIN_HAND ? InteractionHand.MAIN_HAND
                                          : InteractionHand.OFF_HAND;
    }
}
