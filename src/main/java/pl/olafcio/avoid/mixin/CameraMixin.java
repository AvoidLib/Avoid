package pl.olafcio.avoid.mixin;

import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mixininterface.ICamerable;
import pl.olafcio.avoid.net.fluid.AvoidFluid;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.FluidsNative;

@Mixin(Camera.class)
public class CameraMixin implements ICamerable {
    @Shadow        private Level level;
    @Shadow @Final private BlockPos.MutableBlockPos blockPosition;
    @Shadow        private Vec3 position;
    @Shadow        private Entity entity;

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

        if (entity != null)
            ((ICamerable) entity).avoidlib$inAvoidFluid(value);
    }

    @Inject(at = @At("HEAD"), method = "getFluidInCamera")
    public void getFluidInCamera(CallbackInfoReturnable<FogType> cir) {
        avoidlib$inAvoidFluid(null);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;", ordinal = 0), method = "getFluidInCamera", cancellable = true)
    public void getFluidInCamera__getFluidState(CallbackInfoReturnable<FogType> cir) {
        var fluidState = this.level.getFluidState(this.blockPosition);
        var fluid = fluidState.getType();

        if (fluid instanceof AvoidFluid avoid) {
            if (!(this.position.y < this.blockPosition.getY() + fluidState.getHeight(this.level, this.blockPosition)))
                return;

            for (var entry : FluidsNative.instances.entrySet()) {
                if (entry.getValue().isSame(avoid)) {
                    cir.setReturnValue(FogType.NONE);
                    avoidlib$inAvoidFluid(entry.getKey());
                }
            }
        }
    }
}
