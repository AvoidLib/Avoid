package pl.olafcio.avoid_impl.net.fluid.inside_block;

import net.minecraft.world.entity.InsideBlockEffectType;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.fluid.inside_block.InsideBlockAction;

@Native
@ApiStatus.Internal
public final class InsideBlockActionNative {
    @ApiStatus.Internal
    private InsideBlockActionNative() {}

    public static InsideBlockEffectType convert(InsideBlockAction action) {
        if (action == InsideBlockAction.FREEZE)
            return InsideBlockEffectType.FREEZE;
        else if (action == InsideBlockAction.CLEAR_FREEZE)
            return InsideBlockEffectType.CLEAR_FREEZE;
        else if (action == InsideBlockAction.FIRE_IGNITE)
            return InsideBlockEffectType.FIRE_IGNITE;
        else if (action == InsideBlockAction.LAVA_IGNITE)
            return InsideBlockEffectType.LAVA_IGNITE;
        else if (action == InsideBlockAction.EXTINGUISH)
            return InsideBlockEffectType.EXTINGUISH;

        return null;
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
