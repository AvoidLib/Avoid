package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat_server.event.ServerChatSendEvent;
import pl.olafcio.avoid.net.chat_server.event.ServerChatSentEvent;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player.gamemode.GameModeNative;
import pl.olafcio.avoid.net.player_server.PlayerInput;
import pl.olafcio.avoid.net.player_server.event.ServerPlayerGameModeChangeEvent;
import pl.olafcio.avoid.net.player_server.event.ServerPlayerInputEvent;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(at = @At("TAIL"), method = "sendSystemMessage(Lnet/minecraft/network/chat/Component;Z)V", cancellable = true)
    public void sendSystemMessage(Component component, boolean bl, CallbackInfo ci) {
        ServerChatSendEvent sendEvent = new ServerChatSendEvent(
                PlayerNative.convertFrom((ServerPlayer) (Object) this),
                COFromNative.from(component)
        );

        EventManager.fire(sendEvent);

        if (sendEvent.isCancelled()) {
            ci.cancel();
            return;
        }

        EventManager.fire(new ServerChatSentEvent(
                PlayerNative.convertFrom((ServerPlayer) (Object) this),
                COFromNative.from(component)
        ));
    }

    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/OutgoingChatMessage;sendToPlayer(Lnet/minecraft/server/level/ServerPlayer;ZLnet/minecraft/network/chat/ChatType$Bound;)V",
                    shift = At.Shift.AFTER
            ),
            method = "sendChatMessage"
    )
    public void sendChatMessage(OutgoingChatMessage outgoingChatMessage, boolean bl, ChatType.Bound bound, CallbackInfo ci) {
        EventManager.fire(new ServerChatSentEvent(
                PlayerNative.convertFrom((ServerPlayer) (Object) this),
                COFromNative.from(outgoingChatMessage.content())
        ));
    }

    @Inject(at = @At("HEAD"), method = "setGameMode", cancellable = true)
    public void setGameMode(GameType gameType, CallbackInfoReturnable<Boolean> cir) {
        var event = new ServerPlayerGameModeChangeEvent(
                PlayerNative.convertFrom((ServerPlayer) (Object) this),
                GameModeNative.convertFrom(gameType)
        );

        EventManager.fire(event);

        if (event.isCancelled())
            cir.setReturnValue(false);
    }

    @WrapMethod(method = "setLastClientInput")
    public void setLastClientInput(Input input, Operation<Void> original) {
        var event = new ServerPlayerInputEvent(
                PlayerNative.convertFrom((ServerPlayer) (Object) this),
                new PlayerInput(input.forward(), input.backward(), input.left(), input.right(), input.jump(), input.shift(), input.sprint())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            return;

        if (event.isInputChanged()) {
            var eventInput = event.getInput();

            input = new Input(
                    eventInput.forward(),
                    eventInput.backward(),
                    eventInput.left(),
                    eventInput.right(),
                    eventInput.jump(),
                    eventInput.shift(),
                    eventInput.sprint()
            );
        }

        original.call(input);
    }
}
