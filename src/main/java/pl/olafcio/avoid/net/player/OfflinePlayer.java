package pl.olafcio.avoid.net.player;

import java.util.UUID;

public interface OfflinePlayer {
    String getNick();
    UUID uuid();

    default String getName() {
        return getNick().replace("§", "");
    }
}
