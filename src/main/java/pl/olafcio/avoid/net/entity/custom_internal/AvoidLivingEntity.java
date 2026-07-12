package pl.olafcio.avoid.net.entity.custom_internal;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity.values.HandNative;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@ApiStatus.Internal
public final class AvoidLivingEntity extends LivingEntity implements IAvoidEntity {
    private final pl.olafcio.avoid.net.entity.custom.Entity wrappedEntity;

    @Override
    public pl.olafcio.avoid.net.entity.custom.Entity getAvoidEntity() {
        return wrappedEntity;
    }

    public AvoidLivingEntity(EntityType<? extends LivingEntity> entityType, Level level, EntityConstructor constructor) {
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
        super.defineSynchedData(builder);
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        return super.hurtServer(serverLevel, damageSource, f);
    }

    @Override
    public void parentTick() {
        super.tick();
    }

    @Override
    public void tick() {
        wrappedEntity.tick();
    }

    @Override
    public HumanoidArm getMainArm() {
        return HandNative.convertFrom(wrappedEntity.getMainHand());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
    }
}
