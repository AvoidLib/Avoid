package pl.olafcio.avoid.net.item.component.values;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

@Native
@ApiStatus.Internal
public final class _value_type_accessor {
    @ApiStatus.Internal
    private _value_type_accessor() {}

    public static Class<?> getInput(_value_type<?> type) {
        return type.input;
    }

    public static <O> TransformingItemComponentValue<?, O> getController(_value_type<O> type) {
        return type.controller;
    }
}
