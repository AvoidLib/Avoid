package pl.olafcio.avoid_impl.net.entity.custom_internal;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.sync.NetworkSync;
import pl.olafcio.avoid_impl.mixininterface.IDataItem;
import pl.olafcio.avoid_impl.mixininterface.ISynchedEntityData;
import pl.olafcio.avoid_impl.net.entity_type.AvoidEntityType;
import pl.olafcio.avoid_impl.net.entity_type.save.SaveController;
import pl.olafcio.avoid_impl.net.entity_type.sync.Controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

public interface IAvoidEntity {
    Entity getAvoidEntity();
    void parentTick();

    default void avoid_injectSynchedData() {
        // I have to do this as the default values for custom entity data depend on the entity itself

        var self = getEntity();
        var builder = ((ISynchedEntityData) self.entityData).avoidlib$builder();

        avoid_defineSynchedData(builder);

        self.entityData = builder.build();
    }

    private void avoid_defineSynchedData(SynchedEntityData.Builder builder) {
        var synched = getSynchs();

        for (var sync : synched) {
            try {
                unsafeDefine(builder, sync);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to define @NetworkSync field: '%s' on '%s'".formatted(sync.field.getName(), sync.field.getDeclaringClass().getName()), e);
            }
        }
    }

    default @NotNull AvoidEntityType<?> getAvoidType() {
        return (AvoidEntityType<?>) getEntity().getType();
    }

    private ArrayList<Controller<?, ?, ?>> getSynchs() {
        return getAvoidType().synchs;
    }
    private ArrayList<SaveController<?, ?, ?>> getSaved() {
        return getAvoidType().saved;
    }

    private @NotNull net.minecraft.world.entity.Entity getEntity() {
        return (net.minecraft.world.entity.Entity) this;
    }

    @SuppressWarnings("unchecked")
    private <I, O> void unsafeDefine(SynchedEntityData.Builder builder, Controller<?, ?, ?> sync) throws IllegalAccessException {
        sync.field.setAccessible(true);

        var accessor = (EntityDataAccessor<O>) sync.accessor;
        var value = ((Function<I, O>) sync.converterTo).apply((I) sync.field.get(getAvoidEntity()));

        builder.define(accessor, value);

        if (!sync.field.getDeclaredAnnotation(NetworkSync.class).static_default()) {
            ((IDataItem) builder.itemsById[accessor.id()]).avoidlib$setNeverDefault();
        }
    }

    default void avoid_onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        var synched = getSynchs();

        for (var sync : synched) {
            if (sync.accessor.equals(entityDataAccessor)) {
                unsafeSet(entityDataAccessor, sync);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <I, O> void unsafeSet(EntityDataAccessor<I> entityDataAccessor, Controller<?, ?, ?> sync) {
        try {
            sync.field.setAccessible(true);

            var cast = (Controller<O, I, ?>) sync;

            cast.set(getAvoidEntity(), (O) cast.converterFrom.apply(getEntity().entityData.get(entityDataAccessor)));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to update @NetworkSync field: '%s' on '%s'".formatted(sync.field.getName(), sync.field.getDeclaringClass().getName()), e);
        }
    }

    Logger LOGGER = LoggerFactory.getLogger("Avoid/Entity Data");

    default void avoid_readAdditionalSaveData(ValueInput valueInput) {
        var saved = getSaved();

        for (var sync : saved) {
            try {
                sync.field.setAccessible(true);

                var optional = valueInput.read(sync.name, sync.codec);
                if (optional.isEmpty()) {
                    LOGGER.debug("Missing @Saved field: '{}' on '{}' // {}", sync.field.getName(), sync.field.getDeclaringClass().getName(), getEntity());

//                    if (sync.field.getType().isPrimitive())
//                        throw new RuntimeException("Missing @Saved field, cannot assign null to %s: '%s' on '%s'".formatted(sync.field.getType().getName(), sync.field.getName(), sync.field.getDeclaringClass().getName()));
//
//                    sync.field.set(getAvoidEntity(), null);
                } else {
                    unsafeRead(sync, optional);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to set @Saved field: '%s' on '%s'".formatted(sync.field.getName(), sync.field.getDeclaringClass().getName()), e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <I, O> void unsafeRead(SaveController<O, I, ?> sync, Optional<?> optional) throws IllegalAccessException {
        sync.set(getAvoidEntity(), (O) sync.converterFrom.apply((I) optional.get()));
    }

    default void avoid_addAdditionalSaveData(ValueOutput valueOutput) {
        var saved = getSaved();

        for (var sync : saved) {
            unsafeStore(valueOutput, sync);
        }
    }

    @SuppressWarnings("unchecked")
    private <I, O> void unsafeStore(ValueOutput valueOutput, SaveController<O, I, ?> sync) {
        try {
            sync.field.setAccessible(true);

            valueOutput.store(sync.name, sync.codec, sync.converterTo.apply((O) sync.field.get(getAvoidEntity())));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get @Saved field: '%s' on '%s'".formatted(sync.field.getName(), sync.field.getDeclaringClass().getName()), e);
        }
    }
}
