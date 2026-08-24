package pl.olafcio.avoid.net.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.annotations.env.ClientUnsafe;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.client.AvoidLibClient;
import pl.olafcio.avoid.internal.VResourceKey;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.effect.Effect;
import pl.olafcio.avoid.net.effect.instance.EffectInstance;
import pl.olafcio.avoid.net.effect.instance.EffectInstanceNative;
import pl.olafcio.avoid.net.entity.values.Damage;
import pl.olafcio.avoid.net.entity.values.Hand;
import pl.olafcio.avoid.net.entity.values.HandNative;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.block_data.BlockData;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid.net.world.vect3.Vect3;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A Minecraft Entity object.
 * <br/><br/>
 * <b>Properties:</b><br/>
 * <ul>
 *     <li>int &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&emsp;&ensp;{@linkplain Entity#id()}</li>
 *     <li>EntityType &nbsp;&nbsp;&emsp;&emsp;{@linkplain Entity#type()}</li>
 *     <li>IVect3 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&emsp;&nbsp;&emsp;{@linkplain Entity#position()}</li>
 *     <li>IVect3 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&emsp;&emsp;{@linkplain Entity#velocity()}</li>
 *     <li>UUID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ensp;{@linkplain Entity#uuid()}</li>
 *     <li>BaseComponent {@linkplain Entity#getDisplayName()}</li>
 * </ul>
 */
@ApiStatus.NonExtendable
@NeverRemoval
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

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() && ((Entity) obj).underlyingEntity == this.underlyingEntity;
    }

    public int id() {
        return id;
    }

    public EntityType type() {
        return type;
    }

    /**
     * Gets the initial entity position (from the constructor time).
     */
    @Deprecated(since = "v1.15", forRemoval = true)
    public IVect3 position() {
        return position;
    }

    /**
     * Gets the initial entity velocity (from the constructor time).
     */
    @Deprecated(since = "v1.15", forRemoval = true)
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

    @NotNull
    public BaseComponent<?> getNameComponent() {
        return COFromNative.from(underlyingEntity.getName());
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

    /**
     * Adds a scoreboard tag to the entity.
     */
    public void addTag(String tag) {
        underlyingEntity.addTag(tag);
    }

    /**
     * Returns all scoreboard tags used on the entity.
     */
    public Set<String> getTags() {
        return underlyingEntity.getTags();
    }

    /**
     * Removes a scoreboard tag from the entity.
     */
    public void removeTag(String tag) {
        underlyingEntity.removeTag(tag);
    }

    public void setGlowingTag(boolean value) {
        underlyingEntity.setGlowingTag(value);
    }

    public boolean getGlowingTag() {
        return underlyingEntity.hasGlowingTag();
    }

    @IncompatibleChange(reason = "This method had inverted behaviour.",
                        change = "return value",
                        since = "v1.9")
    public boolean hasPhysics() {
        return !underlyingEntity.noPhysics;
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

    public boolean isSilent() {
        return underlyingEntity.isSilent();
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

    /**
     * Returns whether the entity is crawling.<br/>
     * The internal state this checks is whether the entity is <i>swimming</i>, while <b>not</b> in water.
     */
    // I have to admit it is quite funny
    public boolean isVisuallyCrawling() {
        return underlyingEntity.isVisuallyCrawling();
    }

    /**
     * Returns whether the entity is crawling or swimming.<br/>
     * The internal state this checks is whether the entity is <i>"swimming"</i> - as crawling also qualifies.
     */
    public boolean isVisuallySwimming() {
        return underlyingEntity.isVisuallySwimming();
    }

    /**
     * Returns whether the entity has velocity on.
     */
    public boolean isPushable() {
        return underlyingEntity.isPushable();
    }

    public boolean isPushedByFluid() {
        return underlyingEntity.isPushedByFluid();
    }

    /**
     * Returns whether any passengers are on this entity.
     */
    public boolean isVehicle() {
        return underlyingEntity.isVehicle();
    }

    /**
     * Returns whether this entity is naturally capable of being a vehicle <i>(entity with passengers)</i> that a controlling passenger can fly with.
     */
    public boolean isFlyingVehicle() {
        return underlyingEntity.isFlyingVehicle();
    }

    /**
     * Returns whether the entity is riding any other entity.
     */
    public boolean isPassenger() {
        return underlyingEntity.isPassenger();
    }

    /**
     * Returns whether the entity can be picked up as an item (usually by a player).
     */
    public boolean isPickable() {
        return underlyingEntity.isPickable();
    }

    public boolean isFreezing() {
        return underlyingEntity.isFreezing();
    }

    /**
     * Returns whether the entity has a safe-walk mode on.<br/>
     * This, in vanilla, is equal to whether the entity is a player (or maybe mannequin) that is sneaking.
     */
    public boolean isSteppingCarefully() {
        return underlyingEntity.isSteppingCarefully();
    }

    /**
     * Returns whether the entity has a no-Y-bounce mode on.<br/>
     * This, in vanilla, is equal to whether the entity is a player (or maybe mannequin) that is sneaking.
     */
    public boolean isSuppressingBounce() {
        return underlyingEntity.isSuppressingBounce();
    }

    /**
     * Returns whether the entity is rideable in water.
     */
    public boolean dismountsUnderwater() {
        return underlyingEntity.dismountsUnderwater();
    }

    /**
     * Returns whether the entity has a passenger that is controlling it.
     */
    public boolean canControlVehicle() {
        return underlyingEntity.canControlVehicle();
    }

    /**
     * Returns whether the entity is immune to fire.
     */
    public boolean fireImmune() {
        return underlyingEntity.fireImmune();
    }

    /**
     * Returns whether he entity is sensitive to water.<br/>
     * Returns null if the entity is dead ({@link Entity#isAlive()} returns {@code false}).
     */
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

    /**
     * Returns the main hand of the entity.
     */
    public Hand getMainHand() {
        return HandNative.convert(__cast(LivingEntity.class)
                                                     .getMainArm());
    }

    /**
     * Returns the current portal use cooldown.<br/><br/>
     * <b>NOTE:</b> This may not report the correct value on the client.<br/>
     * &ensp;&ensp;&emsp;&nbsp;&emsp;But who knows.
     */
    public int getPortalCooldown() {
        return underlyingEntity.getPortalCooldown();
    }

    /**
     * Sets the portal use cooldown to the default dimension value.<br/><br/>
     * <b>NOTE:</b> This may not do much on the client.<br/>
     * &ensp;&ensp;&emsp;&nbsp;&emsp;But who knows.
     */
    public void setPortalCooldown() {
        underlyingEntity.setPortalCooldown();
    }

    /**
     * Sets the portal use cooldown to the given value.<br/><br/>
     * <b>NOTE:</b> This may not do much on the client.<br/>
     * &ensp;&ensp;&emsp;&nbsp;&emsp;But who knows.
     */
    public void setPortalCooldown(int value) {
        underlyingEntity.setPortalCooldown(value);
    }

    /**
     * Returns the entity's velocity/delta movement.<br/><br/>
     * It's basically the current motion of it.
     */
    public IVect3 getVelocity() {
        return Vect3Native.convert(underlyingEntity.getDeltaMovement());
    }

    /**
     * Sets the entity's velocity/delta movement.<br/><br/>
     * This basically means the entity will tween to the position specified by {@code vel}.<br/>
     * It, of course, cannot go through walls.
     */
    public void setVelocity(Vect3 vel) {
        underlyingEntity.setDeltaMovement(Vect3Native.convertFrom(vel));
    }

    /**
     * Sets whether the entity has its gravity disabled.
     * <br/><br/>
     * Using this method on the client may crash your game, be a no-op or cause desync issues.
     */
    @ClientUnsafe
    public void setNoGravity(boolean value) {
        underlyingEntity.setNoGravity(value);
    }

    /**
     * Returns the standard gravity for the entity.
     * <br><br/>
     * This doesn't account for the no-gravity mode.
     */
    public double getDefaultGravity() {
        return underlyingEntity.getDefaultGravity();
    }

    /**
     * Returns whether the entity object is client-sided.
     */
    public boolean isClient() {
        return underlyingEntity.level().isClientSide();
    }

    /**
     * Returns whether the entity object is server-sided.
     */
    public boolean isServer() {
        return !underlyingEntity.level().isClientSide();
    }

    /**
     * Pushes the specified entity.
     */
    public void push(Entity entity) {
        underlyingEntity.push(EntityNative.convert(entity));
    }

    /**
     * Invoked when a player's hitbox collides with the hitbox of this entity.
     */
    public void playerTouch(Player player) {
        underlyingEntity.playerTouch((net.minecraft.world.entity.player.Player) EntityNative.convert(player));
    }

    /**
     * Marks the entity as needing sync.
     */
    public void sync() {
        underlyingEntity.needsSync = true;
    }

    /**
     * Unleashes the entity from every leash.
     */
    public boolean removeAllLeashes(Player causedBy) {
        return underlyingEntity.dropAllLeashConnections((net.minecraft.world.entity.player.Player) EntityNative.convert(causedBy));
    }

    /**
     * Returns whether this entity is colliding with the given position if it contained the given block.
     */
    public boolean isColliding(BlockPos blockPos, BlockData blockData) {
        return underlyingEntity.isColliding(
                BlockPosNative.convertFrom(blockPos),
                BlockDataNative.convert(blockData)
        );
    }

    /**
     * Causes damage to the entity. Note that this may cause desync on the client.
     * @param amount The amount of HP ({@code  health / 2}) to take.
     */
    @ClientUnsafe
    public void damage(float amount, Damage damage) {
        if (isClient())
            underlyingEntity.hurtClient(new DamageSource(
                    AvoidLibClient.mc.player.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                                             .getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, IdentificationNative.convert(damage.source()))),
                    damage.directEntity() == null ? null : EntityNative.convert(damage.directEntity()),
                    damage.causingEntity() == null ? null : EntityNative.convert(damage.causingEntity()),
                    damage.damageSourcePosition() == null ? null : Vect3Native.convertFrom(damage.damageSourcePosition())
            ));
        else if (isServer())
            underlyingEntity.hurtServer(
                    (ServerLevel) underlyingEntity.level(),
                    new DamageSource(
                            AvoidInternal.server.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                                                 .getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, IdentificationNative.convert(damage.source()))),
                            damage.directEntity() == null ? null : EntityNative.convert(damage.directEntity()),
                            damage.causingEntity() == null ? null : EntityNative.convert(damage.causingEntity()),
                            damage.damageSourcePosition() == null ? null : Vect3Native.convertFrom(damage.damageSourcePosition())
                    ),
                    amount
            );
    }

    private static final Damage DEFAULT_DAMAGE
                   = new Damage(Identification.of("minecraft:generic_kill"));

    /**
     * Causes damage to the entity. Note that this may cause desync on the client.<br/><br/>
     * This uses the {@code minecraft:generic_kill} damage source.
     * @param amount The amount of HP ({@code  health / 2}) to take.
     */
    @ClientUnsafe
    public void damage(float amount) {
        damage(amount, DEFAULT_DAMAGE);
    }

    /**
     * Kills the entity. Note that this may cause desync on the client.<br/><br/>
     * This uses the {@code minecraft:generic_kill} damage source, and in reality, it takes 214_748_364_7 HP.
     */
    @ClientUnsafe
    public void kill() {
        damage(Integer.MAX_VALUE);
    }

    /**
     * Removes the entity from the world.<br/>
     * Note that this may cause desync on the client.<br/><br/>
     */
    @ClientUnsafe
    public void detach() {
        underlyingEntity.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
    }

    /**
     * Makes the entity jump as if it was on the ground.
     * <br/><br/>
     * This method takes the jump strength, block jump factor and jump boost power into consideration.<br/>
     * <i>(Maybe also something more in the future)</i>
     */
    public void jumpFromGround() {
        __cast(LivingEntity.class).jumpFromGround();
    }

    /**
     * Returns whether the entity has despawning disabled.
     * <br/><br/>
     * <b>NOTE:</b> This only works on mob entities.
     */
    public boolean isPersistenceRequired() {
        return __cast(Mob.class).isPersistenceRequired();
    }

    /**
     * Returns whether the entity can pick up loot.
     */
    public boolean canPickUpLoot() {
        return __cast(LivingEntity.class).canPickUpLoot();
    }

    /**
     * Sets whether the entity can pick up loot.
     * <br/><br/>
     * <b>NOTE:</b> This only works on mob entities.
     */
    public void setCanPickUpLoot(boolean value) {
        __cast(Mob.class).setCanPickUpLoot(value);
    }

    /**
     * Makes the entity sleep at the bed at the specified position.
     */
    public void startSleeping(BlockPos blockPos) {
        __cast(LivingEntity.class).startSleeping(BlockPosNative.convertFrom(blockPos));
    }

    /**
     * Makes the entity stop sleeping.
     */
    public void stopSleeping() {
        __cast(LivingEntity.class).stopSleeping();
    }

    /**
     * Returns the active effects of this entity.
     */
    public List<EffectInstance> getEffects() {
        return __cast(LivingEntity.class).getActiveEffects().stream()
                                                            .map(EffectInstanceNative::convert)
                                                            .toList();
    }

    /**
     * Returns the active effects of this entity within an <i>ID->EffectInstance</i> map.
     */
    public HashMap<Identification, EffectInstance> getEffectMap() {
        var map = __cast(LivingEntity.class).getActiveEffectsMap();
        var output = new HashMap<Identification, EffectInstance>();

        for (var effect : map.entrySet()) {
            var id = IdentificationNative.convertFrom(VResourceKey.identifier(effect.getKey().unwrapKey().orElseThrow()));

            output.put(id, new EffectInstance(
                    id,
                    effect.getValue().getDuration(),
                    effect.getValue().getAmplifier() + 1
            ));
        }

        return output;
    }

    /**
     * Returns whether this entity has the given effect.
     * @param effectID The ID of the effect to check whether the entity has.
     */
    public boolean hasEffect(Identification effectID) {
        return __cast(LivingEntity.class).hasEffect(BuiltInRegistries.MOB_EFFECT.get(IdentificationNative.convert(effectID)).orElseThrow());
    }

    /**
     * Adds an effect to an entity.
     * <br><br/>
     * <b>NOTE:</b> Some entities can't accept some/ or all effects.
     * @param effectInstance The effect to add.
     * @return Whether the effect was added.<br/>
     *         This may be {@code false} if the effect isn't assignable to the entity.
     */
    public boolean addEffect(EffectInstance effectInstance) {
        return __cast(LivingEntity.class).addEffect(EffectInstanceNative.convertFrom(effectInstance));
    }

    /**
     * Removes the effect from the entity.
     * @param effectID The ID of the effect to remove.
     * @return Whether the effect was removed.<br/>
     *         This is {@code false} if the entity didn't have the effect.
     */
    public boolean removeEffect(Identification effectID) {
        return __cast(LivingEntity.class).removeEffect(BuiltInRegistries.MOB_EFFECT.get(IdentificationNative.convert(effectID)).orElseThrow());
    }

    /**
     * Removes all effects from the entity.
     */
    public boolean clearEffects() {
        return __cast(LivingEntity.class).removeAllEffects();
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
