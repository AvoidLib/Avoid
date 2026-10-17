package pl.olafcio.avoid.net.player;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.permission.PermissionHolder;

import java.util.UUID;

public interface OfflinePlayer extends PermissionHolder {
    String getNick();
    UUID uuid();

    @NotNull
    default String getName() {
        return getNick().replace("§", "");
    }
}
