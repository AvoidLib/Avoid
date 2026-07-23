package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.ICamerable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getFluidInCamera()Lnet/minecraft/world/level/material/FogType;"), method = "getFov")
    private FogType getFov__getFluidInCamera(Camera instance, Operation<FogType> original) {
        var value = original.call(instance);

        if (((ICamerable) instance).avoidlib$inAvoidFluid() != null)
            return FogType.WATER;

        return value;
    }
}
