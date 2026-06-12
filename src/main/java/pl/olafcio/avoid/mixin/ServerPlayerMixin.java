package pl.olafcio.avoid.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat_server.event.ServerChatSentEvent;
import pl.olafcio.avoid.net.player.PlayerNative;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(at = @At("TAIL"), method = "sendSystemMessage(Lnet/minecraft/network/chat/Component;Z)V")
    public void sendSystemMessage(Component component, boolean bl, CallbackInfo ci) {
        EventManager.fire(new ServerChatSentEvent(
                PlayerNative.convertFrom((ServerPlayer) (Object) this),
                COFromNative.from(component)
        ));
    }
}
