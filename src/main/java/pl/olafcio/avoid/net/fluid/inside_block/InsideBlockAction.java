package pl.olafcio.avoid.net.fluid.inside_block;

import net.minecraft.world.entity.InsideBlockEffectType;

public enum InsideBlockAction {
    FREEZE(InsideBlockEffectType.FREEZE),
    CLEAR_FREEZE(InsideBlockEffectType.CLEAR_FREEZE),
    FIRE_IGNITE(InsideBlockEffectType.FIRE_IGNITE),
    LAVA_IGNITE(InsideBlockEffectType.LAVA_IGNITE),
    EXTINGUISH(InsideBlockEffectType.EXTINGUISH);

    final InsideBlockEffectType insideBlockEffectType;

    InsideBlockAction(InsideBlockEffectType insideBlockEffectType) {
        this.insideBlockEffectType = insideBlockEffectType;
    }
}
