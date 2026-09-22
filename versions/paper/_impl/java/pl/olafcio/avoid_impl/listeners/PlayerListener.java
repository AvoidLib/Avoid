package pl.olafcio.avoid.listeners;

import com.destroystokyo.paper.event.entity.EntityZapEvent;
import io.papermc.paper.block.bed.BedEnterProblem;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerArmSwingEvent;
import io.papermc.paper.event.player.PlayerBedFailEnterEvent;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import org.bukkit.entity.Llama;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.chat.converter_kyori.COFromNative;
import pl.olafcio.avoid.net.chat.converter_kyori.COToNative;
import pl.olafcio.avoid.net.chat_server.event.ServerChatReceivedEvent;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityInteractEvent;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityStruckByBoltEvent;
import pl.olafcio.avoid.net.entity_server.event.llama.ServerLlamaSpitEvent;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player.gamemode.GameMode;
import pl.olafcio.avoid.net.player.gamemode.GameModeNative;
import pl.olafcio.avoid.net.player_server.PlayerInput;
import pl.olafcio.avoid.net.player_server.event.*;
import pl.olafcio.avoid.net.player_server.event.block.bed.ServerPlayerBedSleepFailEvent;
import pl.olafcio.avoid.net.player_server.event.block.bed.ServerPlayerBedSleepStopEvent;
import pl.olafcio.avoid.net.player_server.event.block.bed.ServerPlayerBedSleepSuccessEvent;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.player_server.values.HandTypeNative;
import pl.olafcio.avoid.net.world.WorldNative;

public final class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent bukkitEvent) {
        var event = new ServerEntityInteractEvent(
                EntityNative.convertFrom(Util.convertEntity(bukkitEvent.getRightClicked())),
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer()))
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent bukkitEvent) {
        var event = new ServerChatReceivedEvent(
                bukkitEvent.signedMessage().message(),
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer()))
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent bukkitEvent) {
        var event = new ServerPlayerUpdateAbilitiesEvent(
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer())),
                bukkitEvent.isFlying()
        );

        EventManager.fire(event);

        if (event.isCancelled()) {
            bukkitEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent bukkitEvent) {
        var event = new ServerPlayerKickEvent(
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer())),
                COFromNative.from(bukkitEvent.reason())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);
        else if (event.isReasonChanged())
            bukkitEvent.reason(COToNative.from(event.getReason()));
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent bukkitEvent) {
        var event = new ServerPlayerGameModeChangeEvent(
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer())),
                GameMode.fromID(bukkitEvent.getNewGameMode().getValue())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInput(PlayerInputEvent bukkitEvent) {
        var input = bukkitEvent.getInput();
        var event = new ServerPlayerInputEvent(
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer())),
                new PlayerInput(input.isForward(), input.isBackward(), input.isLeft(), input.isRight(), input.isJump(),
                        input.isSneak(), input.isSprint())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            Avoid.LOGGER.error("Cannot cancel input event on Paper");
        else if (event.isInputChanged())
            Avoid.LOGGER.error("Cannot modify input event on Paper");
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        EventManager.fire(new ServerPlayerBedSleepSuccessEvent(
                PlayerNative.convertFrom(Util.convert(event.getPlayer())),
                WorldNative.make(Util.convert(event.getBed().getWorld())),
                new BlockPos(event.getBed().getX(), event.getBed().getY(), event.getBed().getZ())
        ));
    }

    @EventHandler
    public void onPlayerBedFailEnter(PlayerBedFailEnterEvent bukkitEvent) {
        ServerPlayerBedSleepFailEvent.Reason reason;

        var error = bukkitEvent.enterAction().problem();
        if (error == BedEnterProblem.OTHER)
            reason = ServerPlayerBedSleepFailEvent.Reason.INVALID_PLAYER;
        else if (error == BedEnterProblem.TOO_FAR_AWAY)
            reason = ServerPlayerBedSleepFailEvent.Reason.TOO_FAR_AWAY;
        else if (error == BedEnterProblem.OBSTRUCTED)
            reason = ServerPlayerBedSleepFailEvent.Reason.BLOCKED;
        else if (error == BedEnterProblem.NOT_SAFE)
            reason = ServerPlayerBedSleepFailEvent.Reason.MONSTERS_NEAR;
        else
            reason = ServerPlayerBedSleepFailEvent.Reason.MISC;

        var event = new ServerPlayerBedSleepFailEvent(
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer())),
                reason,
                new BlockPos(bukkitEvent.getBed().getX(), bukkitEvent.getBed().getY(), bukkitEvent.getBed().getZ()),
                WorldNative.make(Util.convert(bukkitEvent.getBed().getWorld()))
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent bukkitEvent) {
        var event = new ServerPlayerBedSleepStopEvent(
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer())),
                WorldNative.make(Util.convert(bukkitEvent.getBed().getWorld())),
                true,
                true
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerArmSwing(PlayerArmSwingEvent bukkitEvent) {
        var event = new ServerPlayerSwingEvent(
                PlayerNative.convertFrom(Util.convert(bukkitEvent.getPlayer())),
                bukkitEvent.getHand() == EquipmentSlot.HAND
                        ? HandType.MAIN_HAND
                        : HandType.OFF_HAND
        );

        EventManager.fire(event);

        if (event.isCancelled())
            bukkitEvent.setCancelled(true);  // at:swing
    }
}
