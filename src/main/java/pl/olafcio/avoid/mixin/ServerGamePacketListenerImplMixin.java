package pl.olafcio.avoid.mixin;

import net.minecraft.network.Connection;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.event.ServerPlayerKickEvent;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin extends ServerCommonPacketListenerImpl {
    @Shadow
    public abstract ServerPlayer getPlayer();

    public ServerGamePacketListenerImplMixin(MinecraftServer minecraftServer, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraftServer, connection, commonListenerCookie);
    }

    @Override
    public void disconnect(DisconnectionDetails disconnectionDetails) {
        var event = new ServerPlayerKickEvent(
                PlayerNative.convertFrom(getPlayer()),
                COFromNative.from(disconnectionDetails.reason())
        );

        EventManager.fire(event);

        if (event.isCancelled())
            return;

        if (event.isReasonChanged())
            disconnectionDetails = new DisconnectionDetails(
                    COToNative.from(event.getReason()),
                    disconnectionDetails.report(),
                    disconnectionDetails.bugReportLink()
            );

        super.disconnect(disconnectionDetails);
    }
}
