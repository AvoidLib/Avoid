package pl.olafcio.avoid_impl.net.entity.custom_internal;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.sync.NetworkSync;
import pl.olafcio.avoid_impl.mixininterface.IDataItem;
import pl.olafcio.avoid_impl.mixininterface.ISynchedEntityData;
import pl.olafcio.avoid_impl.net.entity_type.AvoidEntityType;
import pl.olafcio.avoid_impl.net.entity_type.sync.Controller;

import java.util.ArrayList;
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

    private ArrayList<Controller<?, ?, ?>> getSynchs() {
        return ((AvoidEntityType<?>) getEntity().getType()).synchs;
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
}
