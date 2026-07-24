package pl.olafcio.avoid.mixin;

import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.phys.Vec3;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.mixinclass.EntityUtil;
import pl.olafcio.avoid.mixininterface.ICamerable;
import pl.olafcio.avoid.mixininterface.IEntity;
import pl.olafcio.avoid.mixininterface.IEntityFluidInteraction;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity.event.ClientEntityVelocityEvent;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityInteractEvent;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityVelocityEvent;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityInteractEvent;
import pl.olafcio.avoid.net.fluid.AvoidFluid;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.FluidsNative;
import pl.olafcio.avoid.net.fluid.properties._gravity;
import pl.olafcio.avoid.net.fluid.properties._swimmable;
import pl.olafcio.avoid.net.fluid.properties._unbreatheable;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

import static net.minecraft.world.level.material.FlowingFluid.FALLING;

@Mixin(Entity.class)
public abstract class EntityMixin implements ICamerable, IEntity {
    @Shadow
    public abstract Level level();

    @SuppressWarnings("resource")
    @Inject(at = @At("HEAD"), method = "interact", cancellable = true)
    public void interact(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> cir) {
        if (!level().isClientSide() && player instanceof ServerPlayer) {
            var event = new ServerEntityInteractEvent(
                    EntityNative.convertFrom((Entity) (Object) this),
                    PlayerNative.convertFrom((ServerPlayer) player)
            );

            EventManager.fire(event);

            if (event.isCancelled())
                cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Unique
    @Nullable
    private Fluid avoidFluid = null;

    @Override
    public Fluid avoidlib$inAvoidFluid() {
        return avoidFluid;
    }

    @Override
    public void avoidlib$inAvoidFluid(Fluid value) {
        avoidFluid = value;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"), method = "updateSwimming")
    public boolean updateSwimming__is__fluidTags_water(FluidState instance, TagKey<net.minecraft.world.level.material.Fluid> tagKey, Operation<Boolean> original) {
        if (original.call(instance, tagKey))
            return true;

        for (var entry : FluidsNative.classes.entrySet()) {
            if (instance.getType().isSame(entry.getValue())) {
                if (entry.getKey().isAnnotationPresent(_gravity.class)) {
                    //noinspection resource
                    return (this.level().getFluidState(blockPosition.above(1)).getType().isSame(entry.getValue())) ||
                            instance.getValue(FALLING);
                }
            }
        }

        return false;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"), method = "baseTick")
    private boolean updateFluidOnEyes__isEyeInFluid(Entity instance, TagKey<net.minecraft.world.level.material.Fluid> tagKey, Operation<Boolean> original) {
        if (original.call(instance, tagKey))
            return true;

        for (var fluid : FluidsNative.instances.keySet())
            if (fluid.getClass().isAnnotationPresent(_gravity.class))
                if (((IEntityFluidInteraction) this.fluidInteraction).avoid$isEyeInFluid(fluid))
                    return true;

        return false;
    }

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;wasTouchingWater:Z", opcode = Opcodes.PUTFIELD), method = "updateFluidInteraction")
    public void updateFluidInteraction__wasTouchingWater(Entity instance, boolean value, Operation<Void> original) {
        if (!value) {
            for (var fluid : FluidsNative.instances.keySet()) {
                if (((IEntityFluidInteraction) this.fluidInteraction).avoid$isInFluid(fluid)) {
                    this.resetFallDistance();
                    if (!this.wasTouchingWater && !this.firstTick) {
                        this.doWaterSplashEffect();
                    }

                    value = true;

                    break;
                }
            }
        }

        original.call(instance, value);
    }

    @Shadow        private BlockPos blockPosition;
    @Shadow @Final private EntityFluidInteraction fluidInteraction;
    @Shadow        protected boolean wasTouchingWater;
    @Shadow        protected boolean firstTick;

    @Shadow public abstract void resetFallDistance();
    @Shadow protected abstract void doWaterSplashEffect();

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z", ordinal = 0), method = "getBlockSpeedFactor")
    protected boolean getBlockSpeedFactor__is__water(BlockState blockState, Object block, Operation<Boolean> original) {
        if (original.call(blockState, block))
            return true;

        for (var fluid : FluidsNative.instances.keySet()) {
            if (!fluid.getClass().isAnnotationPresent(_swimmable.class))
                continue;

            var id = BuiltInRegistries.BLOCK.getKey(blockState.getBlock());
            var id2 = fluid.getBlockType();

            if (id.getNamespace().equals(id2.namespace()) && id.getPath().equals(id2.path()))
                return true;
        }

        return false;
    }

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;deltaMovement:Lnet/minecraft/world/phys/Vec3;", opcode = Opcodes.PUTFIELD), method = "setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V")
    public void setDeltaMovement(Entity instance, Vec3 value, Operation<Void> original) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT) {
            var event = new ClientEntityVelocityEvent(
                    EntityNative.convertFromTry(instance),
                    Vect3Native.convert(value),
                    EntityUtil.isLocal(instance)
            );

            EventManager.fire(event);

            if (event.isCancelled())
                return;
            else if (event.isVelocityChanged())
                value = Vect3Native.convertFrom(event.getVelocity());
        } else if (!instance.level().isClientSide()) {
            var event = new ServerEntityVelocityEvent(
                    EntityNative.convertFromTry(instance),
                    Vect3Native.convert(value)
            );

            EventManager.fire(event);

            if (event.isCancelled())
                return;
            else if (event.isVelocityChanged())
                value = Vect3Native.convertFrom(event.getVelocity());
        }

        original.call(instance, value);
    }
}
