package pl.olafcio.avoid_impl.mixin;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.client.event.ClientPlaySoundEvent;
import pl.olafcio.avoid_impl.mixinclass.NativeClientSound;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;debug(Lorg/slf4j/Marker;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"), method = "play", cancellable = true)
    public void play__announce(SoundInstance soundInstance, CallbackInfo ci) {
        var event = new ClientPlaySoundEvent(new NativeClientSound(soundInstance));

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
