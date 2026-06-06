package pl.olafcio.avoid.net.item.component.values;

import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import pl.olafcio.avoid.net.item.component.ItemComponentNative;
import pl.olafcio.avoid.net.item.component.ItemComponentType;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public record TooltipDisplay(boolean hideTooltip, SequencedSet<ItemComponentType<?>> hiddenComponents) {
    public TooltipDisplay withHidden(ItemComponentType<?> dataComponentType, boolean bl) {
        if (this.hiddenComponents.contains(dataComponentType) == bl) {
            return this;
        } else {
            SequencedSet<ItemComponentType<?>> sequencedSet = new ReferenceLinkedOpenHashSet<>(this.hiddenComponents);
            if (bl) {
                sequencedSet.add(dataComponentType);
            } else {
                sequencedSet.remove(dataComponentType);
            }

            return new TooltipDisplay(this.hideTooltip, sequencedSet);
        }
    }

    public boolean shows(ItemComponentType<?> dataComponentType) {
        return !this.hideTooltip && !this.hiddenComponents.contains(dataComponentType);
    }

    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.TooltipDisplay, TooltipDisplay>
    {
        @Override
        public TooltipDisplay transform(net.minecraft.world.item.component.TooltipDisplay value) {
            return new TooltipDisplay(value.hideTooltip(), value.hiddenComponents().stream().map(ItemComponentNative::convertFrom).collect(toSequencedSet()));
        }

        @Override
        public net.minecraft.world.item.component.TooltipDisplay untransform(TooltipDisplay value) {
            return new net.minecraft.world.item.component.TooltipDisplay(value.hideTooltip, value.hiddenComponents.stream()
                                                                                                                   .map(ItemComponentNative::convert)
                                                                                                                   .map(Optional::orElseThrow)
                                                                                                                   .collect(toSequencedSet()));
        }

        // ////////// //
        // / seqSet / //
        // ////////// //

        @SuppressWarnings("unchecked")
        private static <I, R> Function<I, R> castingIdentity() {
            return i -> (R) i;
        }

        private record CollectorImpl<T, A, R>(Supplier<A> supplier,
                                      BiConsumer<A, T> accumulator,
                                      BinaryOperator<A> combiner,
                                      Function<A, R> finisher,
                                      Set<Collector.Characteristics> characteristics
        ) implements Collector<T, A, R> {
            CollectorImpl(Supplier<A> supplier,
                          BiConsumer<A, T> accumulator,
                          BinaryOperator<A> combiner,
                          Set<Characteristics> characteristics) {
                this(supplier, accumulator, combiner, castingIdentity(), characteristics);
            }
        }

        private static final Set<Collector.Characteristics> CH_UNORDERED_ID
                = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH));

        private static <T> CollectorImpl<T, ?, SequencedSet<T>> toSequencedSet() {
            return new CollectorImpl<>(ReferenceLinkedOpenHashSet::new, Set::add,
                    (left, right) -> {
                        if (left.size() < right.size()) {
                            right.addAll(left); return right;
                        } else {
                            left.addAll(right); return left;
                        }
                    },
                    CH_UNORDERED_ID);
        }
    }
}
