package pl.olafcio.avoid.net.fluid.inside_block;

import net.minecraft.world.entity.InsideBlockEffectType;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class InsideBlockActionNative {
    @ApiStatus.Internal
    private InsideBlockActionNative() {}

    public static InsideBlockEffectType convert(InsideBlockAction action) {
        return action.insideBlockEffectType;
    }

    public static InsideBlockAction convertFrom(InsideBlockEffectType action) {
        if (action == InsideBlockEffectType.FREEZE)
            return InsideBlockAction.FREEZE;
        else if (action == InsideBlockEffectType.CLEAR_FREEZE)
            return InsideBlockAction.CLEAR_FREEZE;
        else if (action == InsideBlockEffectType.FIRE_IGNITE)
            return InsideBlockAction.FIRE_IGNITE;
        else if (action == InsideBlockEffectType.LAVA_IGNITE)
            return InsideBlockAction.LAVA_IGNITE;
        else if (action == InsideBlockEffectType.EXTINGUISH)
            return InsideBlockAction.EXTINGUISH;

        return null;
    }
}
