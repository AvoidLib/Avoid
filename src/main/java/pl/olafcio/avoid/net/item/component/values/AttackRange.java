package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record AttackRange(float minRange, float maxRange, float minCreativeRange, float maxCreativeRange, float hitboxMargin, float mobFactor) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.AttackRange, AttackRange>
    {
        @Override
        public AttackRange transform(net.minecraft.world.item.component.AttackRange value) {
            return new AttackRange(value.minRange(), value.maxRange(), value.minCreativeRange(), value.maxCreativeRange(), value.hitboxMargin(), value.mobFactor());
        }

        @Override
        public net.minecraft.world.item.component.AttackRange untransform(AttackRange value) {
            return new net.minecraft.world.item.component.AttackRange(value.minRange, value.maxRange, value.minCreativeRange, value.maxCreativeRange, value.hitboxMargin, value.mobFactor);
        }
    }

    public static final _value_type<AttackRange> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.AttackRange.class, new Controller());
}
