package pl.olafcio.avoid_impl.net.advancement;

import pl.olafcio.avoid.net.advancement.Advancement;

import java.util.function.Supplier;

public interface IAvoidAdvancement {
    Supplier<? extends Advancement> avoid$advancement();
}
