package pl.olafcio.avoid.mixin;

import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat.event.ClientChatReceivedEvent;
import pl.olafcio.avoid.net.chat.tag.ChatTagNative;

@Mixin(ChatComponent.class)
public class ChatHudMixin {
    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V", cancellable = true)
    public void addMessage(Component component, MessageSignature messageSignature, GuiMessageTag guiMessageTag, CallbackInfo ci) {
        var event = new ClientChatReceivedEvent(
                COFromNative.from(component),
                guiMessageTag == null ? null : ChatTagNative.convertFrom(guiMessageTag)
        );

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
