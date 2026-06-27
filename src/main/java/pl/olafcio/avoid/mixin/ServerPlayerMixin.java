package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat_server.event.ServerChatSendEvent;
import pl.olafcio.avoid.net.chat_server.event.ServerChatSentEvent;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player.gamemode.GameModeNative;
import pl.olafcio.avoid.net.player_server.PlayerInput;
import pl.olafcio.avoid.net.player_server.event.ServerPlayerGameModeChangeEvent;
import pl.olafcio.avoid.net.player_server.event.ServerPlayerInputEvent;
import pl.olafcio.avoid.net.player_server.event.block.bed.ServerPlayerBedSleepFailEvent;
import pl.olafcio.avoid.net.player_server.event.block.bed.ServerPlayerBedSleepSuccessEvent;
import pl.olafcio.avoid.net.world.WorldNative;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Shadow public abstract Level level();

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

    @Inject(at = @At("RETURN"), method = "startSleepInBed", cancellable = true)
    public void startSleepInBed(BlockPos blockPos, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir) {
        var original = cir.getReturnValue();
        if (original.left().isPresent()) {
            ServerPlayerBedSleepFailEvent.Reason reason;

            var error = original.left().get();
            if (error == Player.BedSleepingProblem.OTHER_PROBLEM)
                reason = ServerPlayerBedSleepFailEvent.Reason.INVALID_PLAYER;
            else if (error == Player.BedSleepingProblem.TOO_FAR_AWAY)
                reason = ServerPlayerBedSleepFailEvent.Reason.TOO_FAR_AWAY;
            else if (error == Player.BedSleepingProblem.OBSTRUCTED)
                reason = ServerPlayerBedSleepFailEvent.Reason.BLOCKED;
            else if (error == Player.BedSleepingProblem.NOT_SAFE)
                reason = ServerPlayerBedSleepFailEvent.Reason.MONSTERS_NEAR;
            else
                reason = ServerPlayerBedSleepFailEvent.Reason.MISC;

            var event = new ServerPlayerBedSleepFailEvent(
                    PlayerNative.convertFrom((ServerPlayer) (Object) this),
                    reason,
                    BlockPosNative.convert(blockPos),
                    WorldNative.make(this.level())
            );

            EventManager.fire(event);

            if (event.isCancelled())
                cir.cancel();

            return;
        }

        EventManager.fire(new ServerPlayerBedSleepSuccessEvent(
                PlayerNative.convertFrom((ServerPlayer) (Object) this),
                WorldNative.make(this.level()),
                BlockPosNative.convert(blockPos)
        ));
    }
}
