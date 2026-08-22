package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.nbt.NbtCompound;
import pl.olafcio.avoid.net.nbt.NbtNative;

public record BlockEntityData(Identification type, NbtCompound tag) {
    public static final class Controller
                        implements TransformingItemComponentValue<TypedEntityData<BlockEntityType<?>>, BlockEntityData>
    {
        @Override
        public BlockEntityData transform(TypedEntityData<BlockEntityType<?>> value) {
            return new BlockEntityData(
                    IdentificationNative.convertFrom(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(value.type())),
                    (NbtCompound) NbtNative.convertFrom(value.copyTagWithoutId())
            );
        }

        @Override
        public TypedEntityData<BlockEntityType<?>> untransform(BlockEntityData value) {
            return TypedEntityData.of(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE.getValue(IdentificationNative.convert(value.type)),
                    NbtNative.convert(value.tag).asCompound().orElseThrow()
            );
        }
    }

    public static final _value_type<BlockEntityData> TYPE
                  = new _value_type<>(
                          TypedEntityData.codec(BuiltInRegistries.BLOCK_ENTITY_TYPE.byNameCodec()),
                          TypedEntityData.streamCodec(ByteBufCodecs.registry(Registries.BLOCK_ENTITY_TYPE)),
                          new Controller()
                    );
}
