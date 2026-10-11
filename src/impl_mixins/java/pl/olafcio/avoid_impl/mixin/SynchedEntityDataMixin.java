package pl.olafcio.avoid_impl.mixin;

import net.minecraft.network.syncher.SyncedDataHolder;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid_impl.mixininterface.ISynchedEntityData;
import pl.olafcio.avoid_impl.net.entity.custom_internal.IAvoidEntity;
import pl.olafcio.avoid_impl.net.entity_type.AvoidEntityDataAccessor;

import java.util.List;
import java.util.Objects;

@Mixin(SynchedEntityData.class)
public class SynchedEntityDataMixin implements ISynchedEntityData {
    @Shadow
    @Final
    private SynchedEntityData.DataItem<?>[] itemsById;

    @Shadow
    @Final
    private SyncedDataHolder entity;

    @Shadow
    private boolean isDirty;

    @Unique
    private SynchedEntityData.Builder builder;

    @Override
    public SynchedEntityData.Builder avoidlib$builder() {
        return builder;
    }

    @Override
    public void avoidlib$builder(SynchedEntityData.Builder value) {
        builder = value;
    }

    @Inject(at = @At("HEAD"), method = "packDirty")
    public void packDirty(CallbackInfoReturnable<List<SynchedEntityData.DataValue<?>>> cir) {
        if (entity instanceof IAvoidEntity avoidEntity) {
            for (var item : this.itemsById) {
                if (item.getAccessor() instanceof AvoidEntityDataAccessor<?, ?> avoid) {
                    try {
                        avoid.controller.field.setAccessible(true);

                        var value = avoid.controller.field.get(avoidEntity.getAvoidEntity());
                        if (!Objects.equals(avoid.lastKnownValue, value)) {
                            unsafeConvert(item, avoid, value);
                            item.setDirty(true);

                            this.isDirty = true;

                            avoid.lastKnownValue = value;
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to get @NetworkSync field: '%s' on '%s'".formatted(avoid.controller.field.getName(), avoid.controller.field.getDeclaringClass().getName()), e);
                    }
                }
            }
        }
    }

    @Unique
    @SuppressWarnings("unchecked")
    private <Avoid, Native> void unsafeConvert(SynchedEntityData.DataItem<Native> item, AvoidEntityDataAccessor<?, ?> avoid, Avoid value) {
        item.setValue(((AvoidEntityDataAccessor<Native, Avoid>) avoid).controller.converterTo.apply(value));
    }
}
