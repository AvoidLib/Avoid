package pl.olafcio.avoid.net.services.list;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.player.PlayerProfile;
import pl.olafcio.avoid.net.services.list.session.*;

import java.net.InetAddress;
import java.util.UUID;

public abstract class PlayerSessionService {
    public abstract void joinServer(UUID uuid, String authenticationToken, String serverID) throws AuthException;

    @Nullable
    public abstract PlayerOverview hasJoinedServer(String username, String serverID, @Nullable InetAddress ipAddress) throws AuthNotAvailableException;

    @Nullable
    public abstract ProfileProperty getPackedTextures(PlayerProfile var1);

    public abstract PlayerTextures unpackTextures(ProfileProperty var1);

    public PlayerTextures getTextures(PlayerProfile profile) {
        ProfileProperty packed = this.getPackedTextures(profile);
        return packed != null ? this.unpackTextures(packed) : PlayerTextures.EMPTY;
    }

    @Nullable
    public abstract PlayerOverview fetchProfile(UUID uuid, boolean requireSigned);

    public abstract String getSecurePropertyValue(ProfileProperty var1) throws InsecureSignatureException;
}
