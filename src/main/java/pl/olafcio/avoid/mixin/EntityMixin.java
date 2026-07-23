package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mixininterface.ICamerable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityInteractEvent;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.FluidsNative;
import pl.olafcio.avoid.net.fluid.properties._gravity;
import pl.olafcio.avoid.net.player.PlayerNative;

import java.util.stream.Stream;

@Mixin(Entity.class)
public abstract class EntityMixin implements ICamerable {
    @Shadow
    public abstract Level level();

    @SuppressWarnings("resource")
    @Inject(at = @At("HEAD"), method = "interact", cancellable = true)
    public void interact(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
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

        for (var entry : FluidsNative.classes.entrySet())
            if (instance.is(entry.getValue()))
                if (entry.getKey().isAnnotationPresent(_gravity.class))
                    return true;

        return false;
    }

    @Unique
    private net.minecraft.world.level.material.Fluid currentFluid = null;

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"), method = "updateFluidOnEyes")
    private boolean updateFluidOnEyes__isEyeInFluid(Entity instance, TagKey<net.minecraft.world.level.material.Fluid> tagKey, Operation<Boolean> original) {
        if (original.call(instance, tagKey))
            return true;

        for (var entry : FluidsNative.classes.entrySet())
            if (entry.getKey().isAnnotationPresent(_gravity.class))
                if (this.currentFluid == entry.getValue())
                    return true;

        return false;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;getTags()Ljava/util/stream/Stream;"), method = "updateFluidOnEyes")
    private Stream<TagKey<net.minecraft.world.level.material.Fluid>> updateFluidOnEyes__setCurrent(FluidState instance, Operation<Stream<TagKey<net.minecraft.world.level.material.Fluid>>> original) {
        this.currentFluid = instance.getType();
        return original.call(instance);
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/Set;clear()V", shift = At.Shift.AFTER), method = "updateFluidOnEyes")
    private void updateFluidOnEyes__clear(CallbackInfo ci) {
        this.currentFluid = null;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;updateFluidHeightAndDoFluidPushing(Lnet/minecraft/tags/TagKey;D)Z"), method = "updateInWaterStateAndDoWaterCurrentPushing")
    boolean updateInWaterStateAndDoWaterCurrentPushing__updateFluidHeightAndDoFluidPushing(Entity instance, TagKey<net.minecraft.world.level.material.Fluid> tagKey, double d, Operation<Boolean> original) {
        if (original.call(instance, tagKey, d))
            return true;

        for (var fluid : FluidsNative.instances.values())
            if (this.updateFluidHeightAndDoFluidPushing(fluid, 0.014))
                return true;

        return false;
    }

    @Shadow public abstract boolean touchingUnloadedChunk();
    @Shadow public abstract AABB getBoundingBox();
    @Shadow public abstract boolean isPushedByFluid();
    @Shadow public abstract Vec3 getDeltaMovement();
    @Shadow public abstract void setDeltaMovement(Vec3 vec3);

    @Unique
    private boolean updateFluidHeightAndDoFluidPushing(net.minecraft.world.level.material.Fluid fluid, double d) {
        if (this.touchingUnloadedChunk()) {
            return false;
        } else {
            AABB aABB = this.getBoundingBox().deflate(0.001);
            int i = Mth.floor(aABB.minX);
            int j = Mth.ceil(aABB.maxX);
            int k = Mth.floor(aABB.minY);
            int l = Mth.ceil(aABB.maxY);
            int m = Mth.floor(aABB.minZ);
            int n = Mth.ceil(aABB.maxZ);
            double e = 0.0;
            boolean bl = this.isPushedByFluid();
            boolean bl2 = false;
            Vec3 vec3 = Vec3.ZERO;
            int o = 0;
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

            for (int p = i; p < j; p++) {
                for (int q = k; q < l; q++) {
                    for (int r = m; r < n; r++) {
                        mutableBlockPos.set(p, q, r);
                        FluidState fluidState = this.level().getFluidState(mutableBlockPos);
                        if (fluidState.getType().isSame(fluid)) {
                            double f = q + fluidState.getHeight(this.level(), mutableBlockPos);
                            if (f >= aABB.minY) {
                                bl2 = true;
                                e = Math.max(f - aABB.minY, e);
                                if (bl) {
                                    Vec3 vec32 = fluidState.getFlow(this.level(), mutableBlockPos);
                                    if (e < 0.4) {
                                        vec32 = vec32.scale(e);
                                    }

                                    vec3 = vec3.add(vec32);
                                    o++;
                                }
                            }
                        }
                    }
                }
            }

            if (vec3.length() > 0.0) {
                if (o > 0) {
                    vec3 = vec3.scale(1.0 / o);
                }

                if (!((Entity)(Object)this instanceof Player)) {
                    vec3 = vec3.normalize();
                }

                Vec3 vec33 = this.getDeltaMovement();
                vec3 = vec3.scale(d);
                double g = 0.003;
                if (Math.abs(vec33.x) < g && Math.abs(vec33.z) < g && vec3.length() < 0.0045000000000000005) {
                    vec3 = vec3.normalize().scale(0.0045000000000000005);
                }

                this.setDeltaMovement(this.getDeltaMovement().add(vec3));
            }

            // this.fluidHeight.put(tagKey, e);
            // TODO Implement?

            return bl2;
        }
    }
}
