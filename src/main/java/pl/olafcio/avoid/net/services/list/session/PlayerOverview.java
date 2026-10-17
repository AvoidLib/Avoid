package pl.olafcio.avoid.net.services.list.session;

import pl.olafcio.avoid.net.player.PlayerProfile;

import java.util.Set;

public record PlayerOverview(
        PlayerProfile profile,
        Set<ProfileAction> actions
) {
    public PlayerOverview(PlayerProfile profile) {
        this(profile, Set.of());
    }
}
