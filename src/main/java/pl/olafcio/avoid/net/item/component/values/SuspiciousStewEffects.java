package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.BuiltInRegistries;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid_impl.internal.VResourceKey;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

import java.util.Collection;

public record SuspiciousStewEffects(Collection<Effect> effects) {
    public record Effect(Identification effect, int duration) {}

    public static final class Controller
            implements TransformingItemComponentValue<net.minecraft.world.item.component.SuspiciousStewEffects, SuspiciousStewEffects>
    {
        @Override
        public SuspiciousStewEffects transform(net.minecraft.world.item.component.SuspiciousStewEffects value) {
            return new SuspiciousStewEffects(
                    value.effects().stream()
                                   .map(entry -> new Effect(
                                           IdentificationNative.convertFrom(VResourceKey.identifier(entry.effect().unwrapKey().orElseThrow())),
                                           entry.duration()
                                   ))
                                   .toList()
            );
        }

        @Override
        public net.minecraft.world.item.component.SuspiciousStewEffects untransform(SuspiciousStewEffects value) {
            return new net.minecraft.world.item.component.SuspiciousStewEffects(
                    value.effects.stream()
                                 .map(effect -> new net.minecraft.world.item.component.SuspiciousStewEffects.Entry(
                                         BuiltInRegistries.MOB_EFFECT.get(IdentificationNative.convert(effect.effect)).orElseThrow(),
                                         effect.duration
                                 ))
                                 .toList()
            );
        }
    }

    public static final _value_type<SuspiciousStewEffects> TYPE
                  = new _value_type<>(net.minecraft.world.item.component.SuspiciousStewEffects.class, new Controller());
}
