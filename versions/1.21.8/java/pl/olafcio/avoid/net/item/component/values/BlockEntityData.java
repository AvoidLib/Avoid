package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.world.item.component.CustomData;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.nbt.NbtCompound;
import pl.olafcio.avoid.net.nbt.NbtNative;

public record BlockEntityData(Identification type, NbtCompound tag) {
    public static final class Controller
            implements TransformingItemComponentValue<CustomData, BlockEntityData>
    {
        @Override
        public BlockEntityData transform(CustomData value) {
            var tag = value.copyTag();
            var id = tag.getString("id").orElseThrow();

            tag.remove("id");

            return new BlockEntityData(
                    Identification.of(id),
                    (NbtCompound) NbtNative.convertFrom(tag)
            );
        }

        @Override
        public CustomData untransform(BlockEntityData value) {
            var copy = value.tag.deepCopy();
            copy.addProperty("id", value.type.toString());

            return CustomData.of(NbtNative.convert(copy).asCompound().orElseThrow());
        }
    }

    public static final _value_type<BlockEntityData> TYPE
            = new _value_type<>(
                    CustomData.CODEC_WITH_ID,
                    CustomData.STREAM_CODEC,
                    new Controller()
              );
}
