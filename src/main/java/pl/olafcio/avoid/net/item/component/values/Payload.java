package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.item.component.CustomData;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.nbt.NbtCompound;
import pl.olafcio.avoid.net.nbt.NbtNative;

public record Payload(NbtCompound tag) {
    public static final class Controller
                        implements TransformingItemComponentValue<CustomData, Payload>
    {
        @Override
        public Payload transform(CustomData value) {
            return new Payload((NbtCompound) NbtNative.convertFrom(value.copyTag()));
        }

        @Override
        public CustomData untransform(Payload value) {
            return CustomData.of(NbtNative.convert(value.tag).asCompound().orElseThrow());
        }
    }
}
