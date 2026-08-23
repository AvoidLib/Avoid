package pl.olafcio.avoid.net.entity.custom_internal;

import com.mojang.logging.LogUtils;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import pl.olafcio.avoid.mixin.accessors.IVillager;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity.values.HandNative;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@ApiStatus.Internal
public final class AvoidMerchant extends Villager implements IAvoidEntity {
    private final pl.olafcio.avoid.net.entity.custom.Merchant wrappedEntity;

    @Override
    public pl.olafcio.avoid.net.entity.custom.Merchant getAvoidEntity() {
        return wrappedEntity;
    }

    public AvoidMerchant(EntityType<? extends Villager> entityType, Level level, EntityConstructor<pl.olafcio.avoid.net.entity.custom.Merchant> constructor) {
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

    @Override
    public boolean wantsToSpawnGolem(long l) {
        return false;
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
        if (serverLevel.getDifficulty() != Difficulty.PEACEFUL) {
            LOGGER.info("Merchant {} was struck by lightning {}.", this, lightningBolt);

            Witch witch = this.convertTo(EntityType.WITCH, ConversionParams.single(this, false, false), witchx -> {
                witchx.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(witchx.blockPosition()), EntitySpawnReason.CONVERSION, null);
                witchx.setPersistenceRequired();
                ((IVillager) (Object) this).avoid$releaseAllPois();
            });

            if (witch == null) {
                this.vanillaThunderHit(serverLevel);
            }
        } else {
            this.vanillaThunderHit(serverLevel);
        }
    }

    private void vanillaThunderHit(ServerLevel serverLevel) {
        this.setRemainingFireTicks(this.getRemainingFireTicks() + 1);
        if (this.getRemainingFireTicks() == 0) {
            this.igniteForSeconds(8.0F);
        }

        this.hurtServer(serverLevel, this.damageSources().lightningBolt(), 5.0F);
    }

    @Override
    public boolean wantsToPickUp(ServerLevel serverLevel, ItemStack itemStack) {
        return false;
    }
}
