package pl.olafcio.avoid.net.entity.custom;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.effect.instance.EffectInstance;
import pl.olafcio.avoid.net.entity.custom_internal.IAvoidEntity;
import pl.olafcio.avoid.net.entity.values.Damage;
import pl.olafcio.avoid.net.entity.values.Hand;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid.net.world.vect3.Vect3;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class Entity extends pl.olafcio.avoid.net.entity.Entity {
    public Entity(int id, Object... args) {
        super(
                id,
                (EntityType) args[0],
                (IVect3) args[1],
                (IVect3) args[2],
                (UUID) args[3],
                (String) args[4],
                (BaseComponent<?>) args[5],
                (net.minecraft.world.entity.Entity) args[6]
        );
    }

    //======================//
    //==== Overrideable ====//
    //======================//

    @Override
    public Hand getMainHand() {
        return Hand.LEFT;
    }

    @Override
    public void tick() {
        __cast(IAvoidEntity.class).parentTick();
    }

    //================//
    //==== Parent ====//
    //================//

    @Override
    public final int id() {
        return super.id();
    }

    @Override
    public final EntityType type() {
        return super.type();
    }

    @Override
    @Deprecated(since = "v1.15", forRemoval = true)
    public final IVect3 position() {
        return super.position();
    }

    @Override
    @Deprecated(since = "v1.15", forRemoval = true)
    public final IVect3 velocity() {
        return super.velocity();
    }

    @Override
    public final UUID uuid() {
        return super.uuid();
    }

    @Override
    public final String uuidString() {
        return super.uuidString();
    }

    @Override
    public final @Nullable BaseComponent<?> getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    public final @NotNull BaseComponent<?> getNameComponent() {
        return super.getNameComponent();
    }

    @Override
    public final boolean isAlive() {
        return super.isAlive();
    }

    @Override
    public final float getHealth() {
        return super.getHealth();
    }

    @Override
    public final float getMaxHealth() {
        return super.getMaxHealth();
    }

    @Override
    public final void setHealth(float health) {
        super.setHealth(health);
    }

    @Override
    public final void setSpeed(float speed) {
        super.setSpeed(speed);
    }

    @Override
    public final float getAbsorptionAmount() {
        return super.getAbsorptionAmount();
    }

    @Override
    public final float getMaxAbsorption() {
        return super.getMaxAbsorption();
    }

    @Override
    public final void getAbsorptionAmount(float amount) {
        super.getAbsorptionAmount(amount);
    }

    @Override
    public final void setArrowCount(int amount) {
        super.setArrowCount(amount);
    }

    @Override
    public final float getJumpBoostPower() {
        return super.getJumpBoostPower();
    }

    @Override
    public final void addTag(String tag) {
        super.addTag(tag);
    }

    @Override
    public final Set<String> getTags() {
        return super.getTags();
    }

    @Override
    public final void removeTag(String tag) {
        super.removeTag(tag);
    }

    @Override
    public final void setGlowingTag(boolean value) {
        super.setGlowingTag(value);
    }

    @Override
    public final boolean getGlowingTag() {
        return super.getGlowingTag();
    }

    @Override
    public final boolean hasPhysics() {
        return super.hasPhysics();
    }

    @Override
    public final boolean isInWall() {
        return super.isInWall();
    }

    @Override
    public final boolean isInWater() {
        return super.isInWater();
    }

    @Override
    public final boolean isInWaterOrRain() {
        return super.isInWaterOrRain();
    }

    @Override
    public final boolean isInLava() {
        return super.isInLava();
    }

    @Override
    public final boolean isInLiquid() {
        return super.isInLiquid();
    }

    @Override
    public final boolean isInvulnerable() {
        return super.isInvulnerable();
    }

    @Override
    public final boolean isNoGravity() {
        return super.isNoGravity();
    }

    @Override
    public final boolean isSilent() {
        return super.isSilent();
    }

    @Override
    public final void baseTick() {
        super.baseTick();
    }

    @Override
    public final boolean canSprint() {
        return super.canSprint();
    }

    @Override
    public final boolean canFreeze() {
        return super.canFreeze();
    }

    @Override
    public final boolean canBeHitByProjectile() {
        return super.canBeHitByProjectile();
    }

    @Override
    public final boolean canSpawnSprintParticle() {
        return super.canSpawnSprintParticle();
    }

    @Override
    public final boolean isSneaking() {
        return super.isSneaking();
    }

    @Override
    public final boolean isCrouching() {
        return super.isCrouching();
    }

    @Override
    public final boolean isSprinting() {
        return super.isSprinting();
    }

    @Override
    public final boolean isAttackable() {
        return super.isAttackable();
    }

    @Override
    public final boolean isSwimming() {
        return super.isSwimming();
    }

    @Override
    public final boolean isSpectator() {
        return super.isSpectator();
    }

    @Override
    public final boolean isUnderwater() {
        return super.isUnderwater();
    }

    @Override
    public final boolean isInterpolating() {
        return super.isInterpolating();
    }

    @Override
    public final boolean isCustomNameVisible() {
        return super.isCustomNameVisible();
    }

    @Override
    public final boolean isInClouds() {
        return super.isInClouds();
    }

    @Override
    public final boolean isOnFire() {
        return super.isOnFire();
    }

    @Override
    public final boolean isOnRails() {
        return super.isOnRails();
    }

    @Override
    public final boolean isOnPortalCooldown() {
        return super.isOnPortalCooldown();
    }

    @Override
    public final boolean isCurrentlyGlowing() {
        return super.isCurrentlyGlowing();
    }

    @Override
    public final boolean isVisuallyCrawling() {
        return super.isVisuallyCrawling();
    }

    @Override
    public final boolean isVisuallySwimming() {
        return super.isVisuallySwimming();
    }

    @Override
    public final boolean isPushable() {
        return super.isPushable();
    }

    @Override
    public final boolean isPushedByFluid() {
        return super.isPushedByFluid();
    }

    @Override
    public final boolean isVehicle() {
        return super.isVehicle();
    }

    @Override
    public final boolean isFlyingVehicle() {
        return super.isFlyingVehicle();
    }

    @Override
    public final boolean isPassenger() {
        return super.isPassenger();
    }

    @Override
    public final boolean isPickable() {
        return super.isPickable();
    }

    @Override
    public final boolean isFreezing() {
        return super.isFreezing();
    }

    @Override
    public final boolean isSteppingCarefully() {
        return super.isSteppingCarefully();
    }

    @Override
    public final boolean isSuppressingBounce() {
        return super.isSuppressingBounce();
    }

    @Override
    public final boolean dismountsUnderwater() {
        return super.dismountsUnderwater();
    }

    @Override
    public final boolean canControlVehicle() {
        return super.canControlVehicle();
    }

    @Override
    public final boolean fireImmune() {
        return super.fireImmune();
    }

    @Override
    public final @UnknownNullability Boolean isSensitiveToWater() {
        return super.isSensitiveToWater();
    }

    @Override
    public final void lavaIgnite() {
        super.lavaIgnite();
    }

    @Override
    public final void igniteForSeconds(float seconds) {
        super.igniteForSeconds(seconds);
    }

    @Override
    public final void igniteForTicks(int ticks) {
        super.igniteForTicks(ticks);
    }

    @Override
    public final void extinguishFire() {
        super.extinguishFire();
    }

    @Override
    public final void clearFire() {
        super.clearFire();
    }

    @Override
    public final boolean removeAllEffects() {
        return super.removeAllEffects();
    }

    @Override
    public final void resetLastActionTime() {
        super.resetLastActionTime();
    }

    @Override
    public final World getWorld() {
        return super.getWorld();
    }

    @Override
    public int getPortalCooldown() {
        return super.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown() {
        super.setPortalCooldown();
    }

    @Override
    public void setPortalCooldown(int value) {
        super.setPortalCooldown(value);
    }

    @Override
    public final IVect3 getVelocity() {
        return super.getVelocity();
    }

    @Override
    public final void setVelocity(Vect3 vel) {
        super.setVelocity(vel);
    }

    @Override
    public final void setNoGravity(boolean value) {
        super.setNoGravity(value);
    }

    @Override
    public final boolean isClient() {
        return super.isClient();
    }

    @Override
    public final boolean isServer() {
        return super.isServer();
    }

    @Override
    public final boolean isLocal() {
        return super.isLocal();
    }

    @Override
    public final void push(pl.olafcio.avoid.net.entity.Entity entity) {
        super.push(entity);
    }

    @Override
    public final void playerTouch(Player player) {
        super.playerTouch(player);
    }

    @Override
    public final void sync() {
        super.sync();
    }

    @Override
    public final boolean removeAllLeashes(Player causedBy) {
        return super.removeAllLeashes(causedBy);
    }

    @Override
    public final boolean isColliding(BlockPos blockPos, BlockData blockData) {
        return super.isColliding(blockPos, blockData);
    }

    @Override
    public final void damage(float amount) {
        super.damage(amount);
    }

    @Override
    public final void damage(float amount, Damage damage) {
        super.damage(amount, damage);
    }

    @Override
    public final void kill() {
        super.kill();
    }

    @Override
    public final void detach() {
        super.detach();
    }

    @Override
    public final void jumpFromGround() {
        super.jumpFromGround();
    }

    @Override
    public final boolean isPersistenceRequired() {
        return super.isPersistenceRequired();
    }

    @Override
    public final boolean canPickUpLoot() {
        return super.canPickUpLoot();
    }

    @Override
    public final void setCanPickUpLoot(boolean value) {
        super.setCanPickUpLoot(value);
    }

    @Override
    public final void setInvulnerable(boolean value) {
        super.setInvulnerable(value);
    }

    @Override
    public final void setInvisible(boolean value) {
        super.setInvisible(value);
    }

    @Override
    public final double getDefaultGravity() {
        return super.getDefaultGravity();
    }

    @Override
    public final void startSleeping(BlockPos blockPos) {
        super.startSleeping(blockPos);
    }

    @Override
    public final void stopSleeping() {
        super.stopSleeping();
    }

    @Override
    public final HashMap<Identification, EffectInstance> getEffectMap() {
        return super.getEffectMap();
    }

    @Override
    public final List<EffectInstance> getEffects() {
        return super.getEffects();
    }

    @Override
    public final boolean hasEffect(Identification effectID) {
        return super.hasEffect(effectID);
    }

    @Override
    public final boolean addEffect(EffectInstance effectInstance) {
        return super.addEffect(effectInstance);
    }

    @Override
    public final boolean removeEffect(Identification effectID) {
        return super.removeEffect(effectID);
    }

    @Override
    public final boolean clearEffects() {
        return super.clearEffects();
    }
}
