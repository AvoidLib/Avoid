package pl.olafcio.avoid_impl.net.team;

import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.player.OfflinePlayer;
import pl.olafcio.avoid.net.server.Server;
import pl.olafcio.avoid.net.team.Team;

import java.util.HashSet;

public class TeamNative {
    public static @NotNull Team convert(PlayerTeam team) {
        return new Team(team.getName(), new HashSet<>() {
            private boolean setup = false;

            {
                for (var player : team.getPlayers())
                    add(Server.getOfflinePlayer(player));

                setup = true;
            }

            @Override
            public boolean add(OfflinePlayer player) {
                if (setup)
                    team.getPlayers().add(player.getName());

                return super.add(player);
            }

            @Override
            public boolean remove(Object o) {
                if (o instanceof OfflinePlayer player)
                    team.getPlayers().remove(player.getName());

                return super.remove(o);
            }
        });
    }
}
