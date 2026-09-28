package pl.olafcio.avoid_impl.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid_impl.net.payload.AvoidPayload;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(at = @At("HEAD"), method = "handleCustomPayload", cancellable = true)
    public void handleCustomPayload(CustomPacketPayload customPacketPayload, CallbackInfo ci) {
        if (customPacketPayload instanceof AvoidPayload.Payload payload) {
            ci.cancel();  // Cancelling so that client doesn't call 'handleUnknownCustomPayload'
            payload.payload.acceptClient();
        }
    }
}
