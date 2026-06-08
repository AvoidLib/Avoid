package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public record UseEffects(boolean canSprint, boolean interactVibrations, float speedMultiplier) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.UseEffects, UseEffects>
    {
        @Override
        public UseEffects transform(net.minecraft.world.item.component.UseEffects value) {
            return new UseEffects(value.canSprint(), value.interactVibrations(), value.speedMultiplier());
        }

        @Override
        public net.minecraft.world.item.component.UseEffects untransform(UseEffects value) {
            return new net.minecraft.world.item.component.UseEffects(value.canSprint, value.interactVibrations, value.speedMultiplier);
        }
    }
}
