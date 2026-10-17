package pl.olafcio.avoid_impl.net.team;

import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.OfflinePlayer;
import pl.olafcio.avoid.net.server.Server;
import pl.olafcio.avoid.net.team.Team;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;

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
        }) {
            @Override
            public void setDisplayName(BaseComponent<?> displayName) {
                super.setDisplayName(displayName);
                team.setDisplayName(COToNative.from(displayName));
            }

            @Override
            public void setPrefix(BaseComponent<?> prefix) {
                super.setPrefix(prefix);
                team.setPlayerPrefix(COToNative.from(prefix));
            }

            @Override
            public void setSuffix(BaseComponent<?> suffix) {
                super.setSuffix(suffix);
                team.setPlayerSuffix(COToNative.from(suffix));
            }

            @Override
            public void setSeeFriendlyInvisibles(boolean seeFriendlyInvisibles) {
                super.setSeeFriendlyInvisibles(seeFriendlyInvisibles);
                team.setSeeFriendlyInvisibles(seeFriendlyInvisibles);
            }

            @Override
            public void setFriendlyFire(boolean allowFriendlyFire) {
                super.setFriendlyFire(allowFriendlyFire);
                team.setAllowFriendlyFire(allowFriendlyFire);
            }
        };
    }
}
