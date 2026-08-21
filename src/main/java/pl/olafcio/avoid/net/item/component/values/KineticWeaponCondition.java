package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.item.component.KineticWeapon;

public record KineticWeaponCondition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
    static KineticWeaponCondition convert(KineticWeapon.Condition value) {
        return new KineticWeaponCondition(value.maxDurationTicks(), value.minSpeed(), value.minRelativeSpeed());
    }

    static KineticWeapon.Condition convertFrom(KineticWeaponCondition value) {
        return new KineticWeapon.Condition(value.maxDurationTicks(), value.minSpeed(), value.minRelativeSpeed());
    }
}
