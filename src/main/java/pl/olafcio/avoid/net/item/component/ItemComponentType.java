package pl.olafcio.avoid.net.item.component;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.id.Identification;

public abstract class ItemComponentType<T> {
    @NotNull
    public abstract Identification getId();
}
