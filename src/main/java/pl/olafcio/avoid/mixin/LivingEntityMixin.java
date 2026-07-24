package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mixininterface.IEntity;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity.event.ClientEntityCreateEvent;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityCreateEvent;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityDropEvent;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
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
}
