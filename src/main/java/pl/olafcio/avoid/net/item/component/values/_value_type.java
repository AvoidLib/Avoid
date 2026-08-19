package pl.olafcio.avoid.net.item.component.values;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public final class _value_type<O> {
    @ApiStatus.Internal
    public final Class<?> input;

    @ApiStatus.Internal
    public final TransformingItemComponentValue<?, O> controller;

    _value_type(Class<?> input, TransformingItemComponentValue<?, O> controller) {
        this.input = input;
        this.controller = controller;
    }
}
