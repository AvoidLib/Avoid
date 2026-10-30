package pl.olafcio.avoid_impl.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.server.permissions.Permission;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.net.client.Client;
import pl.olafcio.avoid.net.command.ClientCommandManager;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid_impl.brigadier.BrigadierSupport;
import pl.olafcio.avoid_impl.mixin.accessors.ICommandContext;
import pl.olafcio.avoid_impl.net.payload.AvoidPayload;

import java.util.LinkedHashMap;
import java.util.Map;

import static pl.olafcio.avoid_impl.net.permission.PermissionHandlers.createAtom;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow
    private CommandDispatcher<ClientSuggestionProvider> commands;

    @Inject(at = @At("HEAD"), method = "handleCustomPayload", cancellable = true)
    public void handleCustomPayload(CustomPacketPayload customPacketPayload, CallbackInfo ci) {
        if (customPacketPayload instanceof AvoidPayload.Payload payload) {
            ci.cancel();  // Cancelling so that client doesn't call 'handleUnknownCustomPayload'
            payload.payload.acceptClient();
        }
    }

    @Inject(at = @At("TAIL"), method = "handleCommands")
    public void handleCommands(ClientboundCommandsPacket packet, CallbackInfo ci) {
        new BrigadierSupport<ClientSuggestionProvider>() {
            @Override
            protected <T extends ArgumentBuilder<ClientSuggestionProvider, T>> T addNodePermissions(SyntaxTree entry, T node) {
                var perm = entry.getPermission();
                if (perm != null) {
                    if (perm instanceof pl.olafcio.avoid.net.command.annotation.Permission cast) {
                        var atom = createAtom(cast.value());

                        node = node.requires(ctx -> {
                            var perms = ctx.permissions();
                            return perms.hasPermission(atom);
                        });
                    } else if (perm instanceof PermissionLevel cast) {
                        var atom = createAtom(cast.value());
                        var cmdLevel = new Permission.HasCommandLevel(cast.level().__get());

                        node = node.requires(ctx -> {
                            var perms = ctx.permissions();
                            return perms.avoid$hasPermission(atom, cmdLevel);
                        });
                    } else {
                        throw new RuntimeException("Invalid command permission");
                    }
                }

                return node;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected Map<String, ParsedArgument<ClientSuggestionProvider, ?>> getArguments(CommandContext<ClientSuggestionProvider> ctx) {
                return ((ICommandContext<ClientSuggestionProvider>) ctx).avoid$arguments();
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void setArguments(CommandContext<ClientSuggestionProvider> ctx, LinkedHashMap<String, ParsedArgument<ClientSuggestionProvider, ?>> rekeyed) {
                ((ICommandContext<ClientSuggestionProvider>) ctx).avoid$arguments(rekeyed);
            }

            @Override
            protected Executor getExecutor(CommandContext<ClientSuggestionProvider> ctx) {
                return Client.getPlayer();
            }
        }.finishCommands(commands, ClientCommandManager.getInstance());
    }
}
