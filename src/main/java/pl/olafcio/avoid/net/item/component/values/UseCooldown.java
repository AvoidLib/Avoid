package pl.olafcio.avoid.net.item.component.values;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.Optional;

public record UseCooldown(float seconds, @Nullable Identification cooldownGroup) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.UseCooldown, UseCooldown>
    {
        @Override
        public UseCooldown transform(net.minecraft.world.item.component.UseCooldown value) {
            return new UseCooldown(
                    value.seconds(),
                    value.cooldownGroup().isPresent() ? IdentificationNative.convertFrom(value.cooldownGroup().get()) : null
            );
        }

        @Override
        public net.minecraft.world.item.component.UseCooldown untransform(UseCooldown value) {
            return new net.minecraft.world.item.component.UseCooldown(
                    value.seconds,
                    value.cooldownGroup == null ? Optional.empty() : Optional.of(IdentificationNative.convert(value.cooldownGroup))
            );
        }
    }
}
