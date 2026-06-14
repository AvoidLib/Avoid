package pl.olafcio.avoid.net.entity;

import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.Set;
import java.util.UUID;

public abstract class Entity {
    private final int id;
    private final EntityType type;
    private final IVect3 position;
    private final IVect3 velocity;
    private final UUID uuid;
    private final String uuidString;
    private final BaseComponent<?> name;

    protected final net.minecraft.world.entity.Entity underlyingEntity;
    protected final <T> T __cast(Class<T> cls) {
        return cls.cast(underlyingEntity);
    }

    protected final <T> T __castEnv(Class<T> cls, String errorMessage) {
        try {
            return cls.cast(underlyingEntity);
        } catch (ClassCastException e) {
            throw new ImproperEnvironment(errorMessage);
        }
    }

    public Entity(
            int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name,
            net.minecraft.world.entity.Entity underlyingEntity
    ) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.velocity = velocity;
        this.uuid = uuid;
        this.uuidString = uuidString;
        this.name = name;
        this.underlyingEntity = underlyingEntity;
    }

    public int id() {
        return id;
    }

    public EntityType type() {
        return type;
    }

    public IVect3 position() {
        return position;
    }
    public IVect3 velocity() {
        return velocity;
    }

    public UUID uuid() {
        return uuid;
    }
    public String uuidString() {
        return uuidString;
    }

    @Nullable
    public BaseComponent<?> getDisplayName() {
        return name;
    }

    public boolean isAlive() {
        return underlyingEntity.isAlive();
    }

    public float getHealth() {
        if (underlyingEntity instanceof LivingEntity le)
            return le.getHealth();

        return 0;
    }

    public float getMaxHealth() {
        if (underlyingEntity instanceof LivingEntity le)
            return le.getMaxHealth();

        return 0;
    }

    public void setHealth(float health) {
        if (underlyingEntity instanceof LivingEntity le)
            le.setHealth(health);
    }

    public void setSpeed(float speed) {
        if (underlyingEntity instanceof LivingEntity le)
            le.setSpeed(speed);
    }

    public float getAbsorptionAmount() {
        if (underlyingEntity instanceof LivingEntity le)
            return le.getAbsorptionAmount();

        return 0;
    }

    public float getMaxAbsorption() {
        if (underlyingEntity instanceof LivingEntity le)
            return le.getMaxAbsorption();

        return 0;
    }

    public void getAbsorptionAmount(float amount) {
        if (underlyingEntity instanceof LivingEntity le)
            le.setAbsorptionAmount(amount);
    }

    public void setArrowCount(int amount) {
        if (underlyingEntity instanceof LivingEntity le)
            le.setArrowCount(amount);
    }

    public float getJumpBoostPower() {
        if (underlyingEntity instanceof LivingEntity le)
            return le.getJumpBoostPower();

        return 0;
    }

    public void addTag(String tag) {
        underlyingEntity.addTag(tag);
    }

    public Set<String> getTags() {
        return underlyingEntity.getTags();
    }

    public void removeTag(String tag) {
        underlyingEntity.removeTag(tag);
    }

    public void setGlowingTag(boolean value) {
        underlyingEntity.setGlowingTag(value);
    }

    public boolean getGlowingTag() {
        return underlyingEntity.hasGlowingTag();
    }

    public boolean hasPhysics() {
        return underlyingEntity.noPhysics;
    }

    public boolean isInWall() {
        return underlyingEntity.isInWall();
    }

    public boolean isInWater() {
        return underlyingEntity.isInWater();
    }

    public boolean isInWaterOrRain() {
        return underlyingEntity.isInWaterOrRain();
    }

    public boolean isInLava() {
        return underlyingEntity.isInLava();
    }

    public boolean isInLiquid() {
        return underlyingEntity.isInLiquid();
    }

    public boolean isInvulnerable() {
        return underlyingEntity.isInvulnerable();
    }

    public boolean isNoGravity() {
        return underlyingEntity.isNoGravity();
    }

    public void tick() {
        underlyingEntity.tick();
    }

    public void baseTick() {
        underlyingEntity.baseTick();
    }

    public boolean canSprint() {
        return underlyingEntity.canSprint();
    }

    public boolean canFreeze() {
        return underlyingEntity.canFreeze();
    }

    public boolean canBeHitByProjectile() {
        return underlyingEntity.canBeHitByProjectile();
    }

    public boolean canSpawnSprintParticle() {
        return underlyingEntity.canSpawnSprintParticle();
    }

    public boolean isSneaking() {
        return underlyingEntity.isCrouching();
    }

    public boolean isCrouching() {
        return underlyingEntity.isCrouching();
    }

    public boolean isSprinting() {
        return underlyingEntity.isSprinting();
    }

    public boolean isAttackable() {
        return underlyingEntity.isAttackable();
    }

    public boolean isSwimming() {
        return underlyingEntity.isSwimming();
    }

    public boolean isSpectator() {
        return underlyingEntity.isSpectator();
    }

    public boolean isUnderwater() {
        return underlyingEntity.isUnderWater();
    }

    public boolean isInterpolating() {
        return underlyingEntity.isInterpolating();
    }

    public boolean isCustomNameVisible() {
        return underlyingEntity.isCustomNameVisible();
    }

    public boolean isInClouds() {
        return underlyingEntity.isInClouds();
    }

    public boolean isOnFire() {
        return underlyingEntity.isOnFire();
    }

    public boolean isOnRails() {
        return underlyingEntity.isOnRails();
    }

    public boolean isOnPortalCooldown() {
        return underlyingEntity.isOnPortalCooldown();
    }

    public boolean isCurrentlyGlowing() {
        return underlyingEntity.isCurrentlyGlowing();
    }

    public boolean isVisuallyCrawling() {
        return underlyingEntity.isVisuallyCrawling();
    }

    public boolean isVisuallySwimming() {
        return underlyingEntity.isVisuallySwimming();
    }

    public boolean isPushable() {
        return underlyingEntity.isPushable();
    }

    public boolean isPushedByFluid() {
        return underlyingEntity.isPushedByFluid();
    }

    public boolean isVehicle() {
        return underlyingEntity.isVehicle();
    }

    public boolean isFlyingVehicle() {
        return underlyingEntity.isFlyingVehicle();
    }

    public boolean isPassenger() {
        return underlyingEntity.isPassenger();
    }

    public boolean isPickable() {
        return underlyingEntity.isPickable();
    }

    public boolean isFreezing() {
        return underlyingEntity.isFreezing();
    }

    public boolean isSteppingCarefully() {
        return underlyingEntity.isSteppingCarefully();
    }

    public boolean isSuppressingBounce() {
        return underlyingEntity.isSuppressingBounce();
    }

    public boolean dismountsUnderwater() {
        return underlyingEntity.dismountsUnderwater();
    }

    public boolean canControlVehicle() {
        return underlyingEntity.canControlVehicle();
    }

    public boolean fireImmune() {
        return underlyingEntity.fireImmune();
    }

    @UnknownNullability
    public Boolean isSensitiveToWater() {
        if (underlyingEntity instanceof LivingEntity le)
            return le.isSensitiveToWater();

        return null;
    }

    /**
     * Ignites the entity for 15 seconds if it's not immune to fire (dependent on {@link #fireImmune()}).
     */
    public void lavaIgnite() {
        underlyingEntity.lavaIgnite();
    }

    /**
     * Ignites the player for the given amount of seconds.
     * <br/><br/>
     * This method actually just converts the seconds to ticks using the {@code x * 20} formula.
     */
    public void igniteForSeconds(float seconds) {
        underlyingEntity.igniteForSeconds(seconds);
    }

    /**
     * Ignites the player for the given amount of ticks.
     * <br/><br/>
     * A tick is a 1/20 part of a second - which means a second has 20 ticks.
     */
    public void igniteForTicks(int ticks) {
        underlyingEntity.igniteForTicks(ticks);
    }

    /**
     * Extinguishes the entity off fire (which stops him from burning) and plays sound.
     */
    public void extinguishFire() {
        underlyingEntity.extinguishFire();
    }

    /**
     * Extinguishes the entity off fire (which stops him from burning) <b>without playing a sound</b>.<br/>
     * You probably want to use {@link #extinguishFire()} instead.
     */
    public void clearFire() {
        underlyingEntity.clearFire();
    }

    /**
     * Removes all the entity's effects.
     * <br/>
     * Returns {@code true} if the entity had any effect; otherwise, returns {@code false}.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    @SuppressWarnings("resource")
    public boolean removeAllEffects() {
        var le = __cast(LivingEntity.class);
        if (le.level().isClientSide())
            throw new ImproperEnvironment("[Entity#removeAllEffects] This method can only be ran on server entities!");

        return le.removeAllEffects();
    }

    /**
     * Resets the entity's last action time.
     */
    // TODO: What does this actually mean?
    public void resetLastActionTime() {
        __cast(LivingEntity.class).setNoActionTime(0);
    }

    /**
     * Returns a world object representing the world the entity is currently in.
     */
    public World getWorld() {
        return WorldNative.make(__cast(LivingEntity.class).level());
    }

    @Override
    public String toString() {
        return "Entity[" +
                "id=" + id + ", " +
                "type=" + type + ", " +
                "position=" + position + ", " +
                "velocity=" + velocity + ", " +
                "uuid=" + uuidString + ", " +
                "name=" + name + ']';
    }
}
