package pl.olafcio.avoid.net.team;

import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.OfflinePlayer;

import java.util.Set;

/**
 * <b>WARNING:</b> The team system is notoriously insecure. <u>Player names are stored instead of UUID-s</u>.<br/>&emsp;&emsp;&emsp;&emsp;&nbsp;&ensp;&ensp;Go complain to Mojang i'll make avoid like paper (that is fixing some minecraft bugs) and fix this someday later.
 */
public final class Team {
    private final String name;
    private final Set<OfflinePlayer> players;

    private BaseComponent<?> displayName;
    private BaseComponent<?> prefix;
    private BaseComponent<?> suffix;

    private boolean allowFriendlyFire = true;
    private boolean seeFriendlyInvisibles = true;

    public Team(String name, Set<OfflinePlayer> players) {
        this.name = name;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public Set<OfflinePlayer> getPlayers() {
        return players;
    }

    public BaseComponent<?> getDisplayName() {
        return displayName;
    }

    public void setDisplayName(BaseComponent<?> displayName) {
        this.displayName = displayName;
    }

    public BaseComponent<?> getPrefix() {
        return prefix;
    }

    public void setPrefix(BaseComponent<?> prefix) {
        this.prefix = prefix;
    }

    public BaseComponent<?> getSuffix() {
        return suffix;
    }

    public void setSuffix(BaseComponent<?> suffix) {
        this.suffix = suffix;
    }

    public boolean isFriendlyFire() {
        return allowFriendlyFire;
    }

    public boolean seeFriendlyInvisibles() {
        return seeFriendlyInvisibles;
    }
}
