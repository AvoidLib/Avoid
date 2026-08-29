package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.mixininterface.IEntity;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.effect.instance.EffectInstanceNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity.event.ClientEntityCreateEvent;
import pl.olafcio.avoid.net.entity.values.DamageNative;
import pl.olafcio.avoid.net.entity_server.event.*;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract float getHealth();

    @Shadow
    @Final
    private Map<Holder<MobEffect>, MobEffectInstance> activeEffects;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("CTOR_HEAD"), method = "<init>")
    public void init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        if (level.isClientSide())
            EventManager.fire(new ClientEntityCreateEvent(
                    EntityTypeNative.convertFrom(entityType),
                    EntityNative.convertFrom((LivingEntity) (Object) this)
            ));
        else
            EventManager.fire(new ServerEntityCreateEvent(
                    EntityTypeNative.convertFrom(entityType),
                    EntityNative.convertFrom((LivingEntity) (Object) this)
            ));
    }

    @Inject(at = @At("HEAD"), method = "drop", cancellable = true)
    public void drop(ItemStack itemStack, boolean bl, boolean bl2, CallbackInfoReturnable<ItemEntity> cir) {
        if (this.level().isClientSide())
            return;

        var event = new ServerEntityDropEvent(
                EntityNative.convertFrom((LivingEntity) (Object) this),
                ItemStackNative.convertFrom(itemStack)
        );

        EventManager.fire(event);

        if (event.isCancelled())
            cir.setReturnValue(null);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/TagKey;)D", ordinal = 1), method = "aiStep")
    public double aiStep__getFluidHeight(LivingEntity instance, TagKey tagKey, Operation<Double> original) {
        if (instance.isInWater())
            return original.call(instance, tagKey);

        return ((IEntity) instance).avoidlib$currentFluidHeight();
    }

    @Unique
    private boolean ignoreColumn = false;

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"), method = "baseTick")
    public boolean baseTick__isEyeInFluid(LivingEntity instance, TagKey tagKey, Operation<Boolean> original) {
        if (original.call(instance, tagKey))
            return true;

        if (((IEntity) this).avoidlib$currentFluidUnbreathable() && this.isUnderWater()) {
            this.ignoreColumn = true;
            return true;
        }

        return false;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0), method = "baseTick")
    public boolean baseTick__isBubbleColumn(BlockState instance, Block block, Operation<Boolean> original) {
        if (ignoreColumn) {
            ignoreColumn = false;
            return false;
        }

        return original.call(instance, block);
    }

    @WrapMethod(method = "setHealth")
    public void setHealth(float value, Operation<Void> original) {
        var event = new ServerEntitySetHealthEvent(
                EntityNative.convertFromTry(this),
                value,
                this.getHealth()
        );

        EventManager.fire(event);

        if (event.isCancelled())
            return;
        else if (event.isLevelChanged())
            value = event.getLevel();

        original.call(value);
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    public void addEffect__put(MobEffectInstance mobEffectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!this.level().isClientSide()) {
            var event = new ServerEntityEffectAddEvent(
                    EntityNative.convertFromTry(this),
                    EffectInstanceNative.convert(mobEffectInstance)
            );

            EventManager.fire(event);

            if (event.isCancelled())
                cir.setReturnValue(true);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;update(Lnet/minecraft/world/effect/MobEffectInstance;)Z"), method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    public void addEffect__update(MobEffectInstance mobEffectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!this.level().isClientSide()) {
            var event = new ServerEntityEffectUpdateEvent(
                    EntityNative.convertFrom(this),
                    EffectInstanceNative.convert(mobEffectInstance)
            );

            EventManager.fire(event);

            if (event.isCancelled())
                cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEffectNoUpdate", cancellable = true)
    public void removeEffectNoUpdate(Holder<MobEffect> holder, CallbackInfoReturnable<MobEffectInstance> cir) {
        if (this.activeEffects.containsKey(holder)) {
            var event = new ServerEntityEffectRemoveEvent(
                    EntityNative.convertFrom(this),
                    EffectInstanceNative.convert(this.activeEffects.get(holder))
            );

            EventManager.fire(event);

            if (event.isCancelled())
                cir.setReturnValue(null);
        }
    }

    @Unique
    private boolean cancel = false;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER), method = "checkTotemDeathProtection", cancellable = true)
    public void checkTotemDeathProtection__beginResurrection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (cancel) {
            cancel = false;
            cir.setReturnValue(false);
        }
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"), method = "checkTotemDeathProtection")
    public ItemStack checkTotemDeathProtection__beginResurrection(ItemStack instance, Operation<ItemStack> original, DamageSource damageSource) {
        var event = new ServerEntityResurrectEvent(
                EntityNative.convertFromTry(this),
                DamageNative.convert(damageSource, AvoidInternal.getServer().registryAccess())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            cancel = true;

        return instance;
    }
}
