package pl.olafcio.avoid.mixin;

import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.event.ClientChatCommandEvent;
import pl.olafcio.avoid.net.chat.event.ClientChatSentEvent;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;sendChat(Ljava/lang/String;)V"), method = "handleChatInput")
    public void handleChatInput__sendChatMessage(String string, boolean bl, CallbackInfo ci) {
        EventManager.fire(new ClientChatSentEvent(string));
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;sendCommand(Ljava/lang/String;)V"), method = "handleChatInput")
    public void handleChatInput__sendCommand(String string, boolean bl, CallbackInfo ci) {
        EventManager.fire(new ClientChatCommandEvent(string));
    }
}
