package pl.olafcio.avoid_impl.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.api.Avoid;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.mods.events_stage.RegistryContentEvent;
import pl.olafcio.avoid.mods.events_stage.RegistryFrozenEvent;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.platform.RunningEnv;

@Mixin(BuiltInRegistries.class)
public class BuiltInRegistriesMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V"), method = "bootStrap")
    private static void bootStrap__freeze(CallbackInfo ci) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER) {
            var event = new RegistryContentEvent();
            EventManager.fire(event);

            Avoid.INSTANCE.onEarlyInit();
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V", shift = At.Shift.AFTER), method = "bootStrap")
    private static void bootStrap__freeze__after(CallbackInfo ci) {
        var event = new RegistryFrozenEvent();
        EventManager.fire(event);
    }
}
