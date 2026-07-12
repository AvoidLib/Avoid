package pl.olafcio.avoid.net.entity.custom_internal;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@ApiStatus.Internal
public final class AvoidEntity extends Entity implements IAvoidEntity {
    private final pl.olafcio.avoid.net.entity.custom.Entity wrappedEntity;

    @Override
    public pl.olafcio.avoid.net.entity.custom.Entity getAvoidEntity() {
        return wrappedEntity;
    }

    public AvoidEntity(EntityType<?> entityType, Level level, EntityConstructor constructor) {
        super(entityType, level);

        BaseComponent<?> name;

        try {
            name = COFromNative.from(this.getName());
        } catch (Exception e) {
            name = null;
        }

        this.wrappedEntity = constructor.construct(
                this.getId(),
                EntityTypeNative.convertFrom(this.getType()),
                Vect3Native.convert(this.position()),
                Vect3Native.convert(this.getDeltaMovement()),
                this.getUUID(),
                this.getStringUUID(),
                name,
                this
        );
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        //
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        //
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        //
    }
}
