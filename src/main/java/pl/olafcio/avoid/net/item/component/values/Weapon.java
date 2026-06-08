package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record Weapon(int itemDamagePerAttack, float disableBlockingForSeconds) {
    public Weapon(int itemDamagePerAttack) {
        this(itemDamagePerAttack, 0.0F);
    }

    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.Weapon, Weapon>
    {
        @Override
        public Weapon transform(net.minecraft.world.item.component.Weapon value) {
            return new Weapon(value.itemDamagePerAttack(), value.disableBlockingForSeconds());
        }

        @Override
        public net.minecraft.world.item.component.Weapon untransform(Weapon value) {
            return new net.minecraft.world.item.component.Weapon(value.itemDamagePerAttack, value.disableBlockingForSeconds);
        }
    }
}
