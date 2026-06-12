package pl.olafcio.avoid.mixin;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat_server.event.ServerChatReceivedEvent;
import pl.olafcio.avoid.net.player.PlayerNative;

import java.util.function.Predicate;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(at = @At("HEAD"), method = "broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V", cancellable = true)
    public void broadcastChatMessage(PlayerChatMessage playerChatMessage, Predicate<ServerPlayer> predicate, @Nullable ServerPlayer serverPlayer, ChatType.Bound bound, CallbackInfo ci) {
        if (serverPlayer != null) {
            var event = new ServerChatReceivedEvent(playerChatMessage.signedContent(), PlayerNative.convertFrom(serverPlayer));

            EventManager.fire(event);

            if (event.isCancelled())
                ci.cancel();
        }
    }
}
