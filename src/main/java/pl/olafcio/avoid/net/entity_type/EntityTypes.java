package pl.olafcio.avoid.net.entity_type;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.entity.custom.Entity;
import pl.olafcio.avoid.net.entity.custom_internal.AvoidEntity;
import pl.olafcio.avoid.net.entity.custom_internal.AvoidLivingEntity;
import pl.olafcio.avoid.net.entity.custom_internal.EntityConstructor;
import pl.olafcio.avoid.net.entity_type.properties.*;
import pl.olafcio.avoid.net.entity_type.properties.attachment.AttachmentOperation;
import pl.olafcio.avoid.net.entity_type.properties.attribute.AttributeBase;
import pl.olafcio.avoid.net.entity_type.values.AttachmentTypeNative;
import pl.olafcio.avoid.net.entity_type.values.Category;
import pl.olafcio.avoid.net.entity_type.values.CategoryNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Experimental
public final class EntityTypes {
    @ApiStatus.Internal
    private EntityTypes() {}

    public static void register(Identification entityTypeID, Class<? extends Entity> klass, EntityConstructor constructor) {
        var id = IdentificationNative.convert(entityTypeID);

        var category = klass.getAnnotation(_category.class)
                            .value();

        EntityDimensions dimensions;
        if (klass.isAnnotationPresent(_dimensions.class)) {
            var annotation = klass.getAnnotation(_dimensions.class);

            dimensions = EntityDimensions.scalable(
                    annotation.width(),
                    annotation.height()
            );
        } else {
            dimensions = EntityDimensions.scalable(0.6f, 1.8f);
        }

        if (klass.isAnnotationPresent(_eyeHeight.class)) {
            dimensions = dimensions.withEyeHeight(klass.getAnnotation(_eyeHeight.class)
                                                       .value());
        }

        var attachments = EntityAttachments.builder();
        if (klass.isAnnotationPresent(_attachments.class)) {
            var annotation = klass.getAnnotation(_attachments.class);
            for (var attachment : annotation.value()) {
                var type = AttachmentTypeNative.convertFrom(attachment.type());
                var fallback = type.createFallbackPoints(dimensions.width(), dimensions.height()).getFirst();

                var x = attachment.x();
                var y = attachment.y();
                var z = attachment.z();

                if (attachment.op() == AttachmentOperation.SET) {
                    if (x == Double.MIN_VALUE) x = fallback.x();
                    if (y == Double.MIN_VALUE) y = fallback.y();
                    if (z == Double.MIN_VALUE) z = fallback.z();
                } else if (attachment.op() == AttachmentOperation.ADD) {
                    if (x == Double.MIN_VALUE) x = 0;
                    if (y == Double.MIN_VALUE) y = 0;
                    if (z == Double.MIN_VALUE) z = 0;
                } else if (attachment.op() == AttachmentOperation.MULTIPLY) {
                    if (x == Double.MIN_VALUE) x = 1;
                    if (y == Double.MIN_VALUE) y = 1;
                    if (z == Double.MIN_VALUE) z = 1;
                }

                attachments.attach(type, attachment.op() == AttachmentOperation.SET      ? new Vec3(x, y, z) :
                                         attachment.op() == AttachmentOperation.ADD      ? new Vec3(x, y, z) :
                                         attachment.op() == AttachmentOperation.MULTIPLY ? new Vec3(x, y, z) :
                                                                                           null);
            }
        }

        boolean living = klass.isAnnotationPresent(_living.class);

        if (living) {
            var Type = createType(
                    klass,
                    id,
                    (EntityType<@NotNull LivingEntity> type, Level level)
                        -> new AvoidLivingEntity(type, level, constructor),
                    category,
                    dimensions,
                    attachments
            );

            LIVING_ENTITIES.put(Type, () -> createAttributes(klass));
        } else {
            createType(
                    klass,
                    id,
                    (EntityType<net.minecraft.world.entity.@NotNull Entity> type, Level level)
                            -> new AvoidEntity(type, level, constructor),
                    category,
                    dimensions,
                    attachments
            );
        }
    }

    private static <T extends net.minecraft.world.entity.Entity> @NotNull EntityType<T> createType(
            Class<? extends Entity> klass,
            Identifier id,
            EntityType.EntityFactory<T> factory,
            Category category,
            EntityDimensions dimensions,
            EntityAttachments.Builder attachments
    ) {
        EntityType<T> Type;

        // TODO: Should I use the builder? Guess we'll see when there are issues in cross-version compatibility ;d
        Registry.register(BuiltInRegistries.ENTITY_TYPE, id, Type = new EntityType<T>(
                factory,
                CategoryNative.convertFrom(category),
                !klass.isAnnotationPresent(_noSave.class),
                !klass.isAnnotationPresent(_noSummon.class),
                klass.isAnnotationPresent(_fireImmune.class),
                klass.isAnnotationPresent(_canSpawnFarFromPlayer.class) ||
                     category == Category.CREATURE || category == Category.MISC,
                ImmutableSet.of(),  // immuneTo (blocks)
                dimensions.withAttachments(attachments),
                klass.isAnnotationPresent(_spawnDimensionsScale.class)
                        ? klass.getAnnotation(_spawnDimensionsScale.class)
                               .value()
                        : 1.0f,  // default 'spawn_dimensions_scale' as in EntityType\Builder
                klass.isAnnotationPresent(_clientTrackingRange.class)
                        ? klass.getAnnotation(_clientTrackingRange.class)
                               .value()
                        : 5,  // default 'client_tracking_range' as in EntityType\Builder
                klass.isAnnotationPresent(_updateInterval.class)
                        ? klass.getAnnotation(_updateInterval.class)
                               .value()
                        : 3,  // default 'update_interval' as in EntityType\Builder
                Util.makeDescriptionId("entity", id),
                klass.isAnnotationPresent(_noLootTable.class)
                        ? Optional.empty()
                        : Optional.of(ResourceKey.create(Registries.LOOT_TABLE, id.withPrefix("entities/"))),
                klass.isAnnotationPresent(_featureFlags.class)
                        ? FeatureFlags.REGISTRY.fromNames(Arrays.stream(klass.getAnnotation(_featureFlags.class)
                                                                             .value())
                                                                .map(flag -> Identifier.fromNamespaceAndPath(flag.namespace(), flag.path()))
                                                                .toList())
                        : FeatureFlags.VANILLA_SET,
                !klass.isAnnotationPresent(_notInPeaceful.class)
        ));

        return Type;
    }

    private static AttributeSupplier createAttributes(Class<? extends Entity> klass) {
        var attributes = klass.getAnnotation(_attributes.class);
        var builder = attributes.base() == AttributeBase.LIVING_ENTITY ? LivingEntity.createLivingAttributes() :
                      attributes.base() == AttributeBase.ANIMAL        ? Animal.createAnimalAttributes()       :
                                                                         Mob.createMobAttributes();

        var values = attributes.attributes();
        for (var entry : values)
            builder.add(entry.type().getMinecraft(), entry.value());

        return builder.build();
    }

    static HashMap<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier>> LIVING_ENTITIES
     = new HashMap<>();
}
