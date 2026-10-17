package pl.olafcio.avoid_impl.net.permission;

import me.lucko.fabric.api.permissions.v0.OfflinePermissionCheckEvent;
import me.lucko.fabric.api.permissions.v0.PermissionCheckEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.permission.PermissionHandler;
import pl.olafcio.avoid.net.permission.PermissionHolder;
import pl.olafcio.avoid.net.player.OfflinePlayer;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.server.Server;
import pl.olafcio.avoid_impl.mixinclass.MyUnknownExecutor;
import net.fabricmc.fabric.api.util.TriState;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class PermissionHandlers {
    @ApiStatus.Internal
    private PermissionHandlers() {}

    public static final ArrayList<PermissionHandler> PERMISSION_HANDLERS
                  = new ArrayList<>();

    public static void register(PermissionHandler permissionHandler) {
        PERMISSION_HANDLERS.add(permissionHandler);

        OfflinePermissionCheckEvent.EVENT.register(new OfflinePermissionCheckEvent() {
            @Override
            public @NotNull CompletableFuture<TriState> onPermissionCheck(@NotNull UUID uuid, @NotNull String node) {
                return CompletableFuture.supplyAsync(() -> convert(permissionHandler.hasPermission(new OfflinePlayer() {
                    @Override
                    public String getNick() {
                        return Server.getServices().playerQuery().resolve(uuid);
                    }

                    @Override
                    public UUID uuid() {
                        return uuid;
                    }
                }, node).has()));
            }
        });

        PermissionCheckEvent.EVENT.register(new PermissionCheckEvent() {
            @Override
            public TriState onPermissionCheck(@NotNull SharedSuggestionProvider sharedSuggestionProvider, @NotNull String node) {
                PermissionHolder executor;

                search:
                {
                    if (sharedSuggestionProvider instanceof CommandSourceStack css) {
                        var player = css.getPlayer();
                        if (player != null) {
                            executor = PlayerNative.convertFrom(player);
                            break search;
                        }

                        executor = new MyUnknownExecutor(css);
                    } else {
                        return TriState.DEFAULT;
                    }
                }

                return convert(permissionHandler.hasPermission(executor, node).has());
            }
        });
    }

    private static TriState convert(Boolean value) {
        if (value == null)
            return TriState.DEFAULT;
        else if (value)
            return TriState.TRUE;
        else
            return TriState.FALSE;
    }
}
