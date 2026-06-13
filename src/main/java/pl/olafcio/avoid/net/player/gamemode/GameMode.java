package pl.olafcio.avoid.net.player.gamemode;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Experimental
public enum GameMode {
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure"),
    SPECTATOR(3, "spectator");

    final int index;
    final String name;

    GameMode(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Nullable
    public static GameMode fromID(int index) {
        if (index == 0)
            return GameMode.SURVIVAL;
        else if (index == 1)
            return GameMode.CREATIVE;
        else if (index == 2)
            return GameMode.ADVENTURE;
        else if (index == 3)
            return GameMode.SPECTATOR;
        else
            return null;
    }
}
