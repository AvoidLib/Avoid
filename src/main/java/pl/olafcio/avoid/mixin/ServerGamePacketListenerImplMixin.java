package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.Connection;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Abilities;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player_server.event.ServerPlayerKickEvent;
import pl.olafcio.avoid.net.player_server.event.ServerPlayerUpdateAbilitiesEvent;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin extends ServerCommonPacketListenerImpl {
    @Shadow
    public abstract ServerPlayer getPlayer();

    public ServerGamePacketListenerImplMixin(MinecraftServer minecraftServer, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraftServer, connection, commonListenerCookie);
    }

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z", opcode = Opcodes.PUTFIELD), method = "handlePlayerAbilities")
    public void handlePlayerAbilities__flying(Abilities instance, boolean value, Operation<Void> original) {
        var event = new ServerPlayerUpdateAbilitiesEvent(
                PlayerNative.convertFrom(getPlayer()),
                value
        );

        EventManager.fire(event);

        if (event.isCancelled()) {
            this.getPlayer().onUpdateAbilities();
        } else {
            original.call(instance, value);
        }
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
