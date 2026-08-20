package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

public final class _value_type<O> {
    final Class<?> input;
    final TransformingItemComponentValue<?, O> controller;

    _value_type(Class<?> input, TransformingItemComponentValue<?, O> controller) {
        this.input = input;
        this.controller = controller;
    }
}
