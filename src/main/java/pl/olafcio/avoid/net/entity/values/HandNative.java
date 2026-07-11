package pl.olafcio.avoid.net.entity.values;

import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class HandNative {
    @ApiStatus.Internal
    private HandNative() {}

    public static Hand convert(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? Hand.LEFT :
                                         Hand.RIGHT;
    }

    public static HumanoidArm convertFrom(Hand hand) {
        return hand == Hand.LEFT ? HumanoidArm.LEFT :
                                   HumanoidArm.RIGHT;
    }
}
