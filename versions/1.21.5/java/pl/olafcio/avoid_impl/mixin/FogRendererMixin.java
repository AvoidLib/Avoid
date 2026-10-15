package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid_impl.mixininterface.IFogFunction;

import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Unique
    private static FogType fogType;

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FogRenderer;getPriorityFogFunction(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/FogRenderer$MobEffectFogFunction;"), method = {"computeFogColor", "setupFog"})
    private static FogRenderer.MobEffectFogFunction getPriorityFogFunction__call(Entity entity, float f, Operation<FogRenderer.MobEffectFogFunction> original, @Local FogType fogType) {
        FogRendererMixin.fogType = fogType;

        return original.call(entity, f);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"), method = "getPriorityFogFunction")
    private static <T extends FogRenderer.MobEffectFogFunction> Stream<T> getPriorityFogFunction__filter(Stream<T> instance, Predicate<? super T> predicate, Operation<Stream<T>> original, @Local(argsOnly = true) Entity entity) {
        return original.call(instance, (Predicate<? super T>) key -> {
            return predicate.test(key) || (key instanceof IFogFunction iff && iff.avoidlib$isApplicable(fogType, entity));
        });
    }

    @Unique
    private static boolean setupped = false;

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FogRenderer;getPriorityFogFunction(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/FogRenderer$MobEffectFogFunction;"), method = "setupFog")
    private static FogRenderer.MobEffectFogFunction setupFog__getPriorityFogFunction(
            Entity entity, float f,
            Operation<FogRenderer.MobEffectFogFunction> original,
            @Local(argsOnly = true) Camera camera,
            @Local FogType fogType,
            @Local FogRenderer.FogData fogData
    ) {
        var mobEffectFogFunction = original.call(entity, f);
        if (mobEffectFogFunction instanceof IFogFunction iff) {
            iff.avoidlib$setupFog(fogData, entity, camera.getBlockPosition(), (ClientLevel) entity.level(), f);
            setupped = true;
        }

        return mobEffectFogFunction;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FogRenderer;getPriorityFogFunction(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/FogRenderer$MobEffectFogFunction;", shift = At.Shift.AFTER), method = "setupFog", cancellable = true)
    private static void setupFog__getPriorityFogFunction__after(
            Camera camera,
            FogRenderer.FogMode fogMode,
            Vector4f vector4f,
            float f, boolean bl, float g,
            CallbackInfoReturnable<FogParameters> cir,
            @Local FogRenderer.FogData fogData
    ) {
        if (setupped)
            cir.setReturnValue(new FogParameters(fogData.start, fogData.end, fogData.shape, vector4f.x, vector4f.y, vector4f.z, vector4f.w));
    }
}
