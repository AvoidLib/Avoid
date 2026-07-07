package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record OminousBottleAmplifier(int level) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.OminousBottleAmplifier, OminousBottleAmplifier>
    {
        @Override
        public OminousBottleAmplifier transform(net.minecraft.world.item.component.OminousBottleAmplifier value) {
            return new OminousBottleAmplifier(value.value());
        }

        @Override
        public net.minecraft.world.item.component.OminousBottleAmplifier untransform(OminousBottleAmplifier value) {
            return new net.minecraft.world.item.component.OminousBottleAmplifier(value.level);
        }
    }
}
