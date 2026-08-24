package pl.olafcio.avoid.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;

@Mixin(BuiltInRegistries.class)
public class BuiltInRegistriesMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V"), method = "bootStrap")
    private static void bootStrap__freeze(CallbackInfo ci) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
            Avoid.INSTANCE.onEarlyInit();
    }
}
