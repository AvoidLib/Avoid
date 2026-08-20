package pl.olafcio.avoid.net.effect.values;

import net.minecraft.world.effect.MobEffectCategory;

public enum Category {
    BENEFICIAL(MobEffectCategory.BENEFICIAL),
    HARMFUL(MobEffectCategory.HARMFUL),
    NEUTRAL(MobEffectCategory.NEUTRAL);

    final MobEffectCategory object;

    Category(MobEffectCategory object) {
        this.object = object;
    }
}
