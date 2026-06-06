package pl.olafcio.avoid.net.item.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.id.IdentificationNative;

import java.util.Optional;

@Native
@NullMarked
@ApiStatus.Internal
public final class ItemComponentNative {
    @ApiStatus.Internal
    private ItemComponentNative() {}

    public static Optional<DataComponentType<?>> convert(ItemComponentType<?> input) {
        return BuiltInRegistries.DATA_COMPONENT_TYPE.getOptional(IdentificationNative.convert(input.getId()));
    }

    public static ItemComponentType<?> convertFrom(DataComponentType<?> input) {
        return ItemComponents.LOOKUP.get(IdentificationNative.convertFrom(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(input)).toString());
    }
}
