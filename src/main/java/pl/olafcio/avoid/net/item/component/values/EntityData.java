package pl.olafcio.avoid.net.item.component.values;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.TypedEntityData;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.nbt.NbtCompound;
import pl.olafcio.avoid.net.nbt.NbtNative;

public record EntityData(Identification type, NbtCompound tag) {
    public static final class Controller
                        implements TransformingItemComponentValue<TypedEntityData<EntityType<?>>, EntityData>
    {
        @Override
        public EntityData transform(TypedEntityData<EntityType<?>> value) {
            return new EntityData(
                    IdentificationNative.convertFrom(BuiltInRegistries.ENTITY_TYPE.getKey(value.type())),
                    (NbtCompound) NbtNative.convertFrom(value.copyTagWithoutId())
            );
        }

        @Override
        public TypedEntityData<EntityType<?>> untransform(EntityData value) {
            return TypedEntityData.of(
                    BuiltInRegistries.ENTITY_TYPE.getValue(IdentificationNative.convert(value.type)),
                    NbtNative.convert(value.tag).asCompound().orElseThrow()
            );
        }
    }

    public static final _value_type<EntityData> TYPE
                  = new _value_type<>(
                          TypedEntityData.codec(EntityType.CODEC),
                          TypedEntityData.streamCodec(EntityType.STREAM_CODEC),
                          new Controller()
                    );
}
