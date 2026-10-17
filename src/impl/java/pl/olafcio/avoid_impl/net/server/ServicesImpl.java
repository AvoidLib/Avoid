package pl.olafcio.avoid_impl.net.server;

import com.google.common.collect.ImmutableMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.SignatureState;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.InsecurePublicKeyException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.ProfileActionType;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.server.players.UserNameToIdResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.player.PlayerProfile;
import pl.olafcio.avoid.net.services.Services;
import pl.olafcio.avoid.net.services.list.PlayerQueryService;
import pl.olafcio.avoid.net.services.list.PlayerSessionService;
import pl.olafcio.avoid.net.services.list.session.*;

import java.net.InetAddress;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicesImpl extends Services {
    private final net.minecraft.server.Services services;

    public ServicesImpl(net.minecraft.server.Services services) {
        this.services = services;
    }

    @Override
    @NotNull
    public PlayerQueryService playerQuery() {
        return new PlayerQueryService() {
            private final UserNameToIdResolver userNameToIdResolver
                    = services.nameToIdCache();

            @Override
            public String resolve(UUID uuid) {
                var optional = userNameToIdResolver.get(uuid);
                if (optional.isEmpty())
                    return null;

                return optional.get().name();
            }

            @Override
            public UUID resolve(String nick) {
                var optional = userNameToIdResolver.get(nick);
                if (optional.isEmpty())
                    return null;

                return optional.get().id();
            }
        };
    }

    @Override
    @NotNull
    public PlayerSessionService playerSession() {
        return new PlayerSessionService() {
            private final MinecraftSessionService sessionService
                    = services.sessionService();

            @Override
            public void joinServer(UUID uuid, String authenticationToken, String serverID) throws AuthException {
                try {
                    sessionService.joinServer(uuid, authenticationToken, serverID);
                } catch (AuthenticationException e) {
                    throw new AuthException("[PlayerSession#joinServer] Authentication error: " + e.getMessage(), e);
                }
            }

            private static PlayerOverview convert(ProfileResult result) {
                return new PlayerOverview(
                        PlayerNative.convertProfile(result.profile()),
                        result.actions().stream()
                                .map(a -> a == ProfileActionType.FORCED_NAME_CHANGE ? ProfileAction.FORCED_NAME_CHANGE :
                                        a == ProfileActionType.USING_BANNED_SKIN ? ProfileAction.USING_BANNED_SKIN :
                                                null)
                                .filter(a -> a != null)
                                .collect(Collectors.toUnmodifiableSet())
                );
            }

            private interface ProfilePropertyImpl
                    extends ProfileProperty {
                Property getInternal();
            }

            private static ProfileProperty convert(Property property) {
                return new ProfilePropertyImpl() {
                    @Override
                    public String name() {
                        return property.name();
                    }

                    @Override
                    public String value() {
                        return property.value();
                    }

                    @Override
                    public @Nullable String signature() {
                        return property.signature();
                    }

                    @Override
                    public Property getInternal() {
                        return property;
                    }
                };
            }

            @Override
            public @Nullable PlayerOverview hasJoinedServer(String username, String serverID, @Nullable InetAddress ipAddress) throws AuthNotAvailableException {
                try {
                    var result = sessionService.hasJoinedServer(username, serverID, ipAddress);
                    if (result == null)
                        return null;

                    return convert(result);
                } catch (AuthenticationUnavailableException e) {
                    throw new AuthNotAvailableException("[PlayerSession#hasJoinedServer] Authentication unavailable: " + e.getMessage(), e);
                }
            }

            @Override
            @Nullable
            public ProfileProperty getPackedTextures(PlayerProfile var1) {
                var input = var1.properties();
                var output = ImmutableMultimap.<String, Property>builder();

                for (var entry : input.entrySet())
                    //noinspection unchecked
                    output.putAll(entry.getKey(), (Collection<? extends Property>) entry.getValue());

                var value = sessionService.getPackedTextures(new GameProfile(var1.id(), var1.name(), new PropertyMap(output.build())));
                if (value == null)
                    return null;

                return convert(value);
            }

            @SuppressWarnings("unchecked")
            private static Map<String, String> getMetadata(MinecraftProfileTexture mpt) {
                // AW doesn't work on it. Aside, this would have to be done on NeoForge anyway.
                var fields = mpt.getClass().getDeclaredFields();

                for (var field : fields) {
                    if (field.getType() == Map.class) {
                        field.setAccessible(true);

                        try {
                            return (Map<String, String>) field.get(mpt);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("[PlayerSessionService#unpackTextures] Failed to get metadata of MinecraftProfileTexture  (illegal access)", e);
                        }
                    }
                }

                throw new RuntimeException("[PlayerSessionService#unpackTextures] Failed to get metadata of MinecraftProfileTexture  (field not found)");
            }

            @Override
            public PlayerTextures unpackTextures(ProfileProperty var1) {
                var value = sessionService.unpackTextures(((ProfilePropertyImpl) var1).getInternal());
                return new PlayerTextures(
                        value.skin() == null ? null : new PlayerTexture(URI.create(value.skin().getUrl()), getMetadata(value.skin())),
                        value.cape() == null ? null : new PlayerTexture(URI.create(value.cape().getUrl()), getMetadata(value.cape())),
                        value.elytra() == null ? null : new PlayerTexture(URI.create(value.elytra().getUrl()), getMetadata(value.elytra())),
                        value.signatureState() == SignatureState.INVALID ? SignatureSpec.INVALID :
                                value.signatureState() == SignatureState.SIGNED ? SignatureSpec.SIGNED :
                                        SignatureSpec.UNSIGNED
                );
            }

            @Override
            @Nullable
            public PlayerOverview fetchProfile(UUID uuid, boolean requireSigned) {
                var value = sessionService.fetchProfile(uuid, requireSigned);
                if (value == null)
                    return null;

                return convert(value);
            }

            @Override
            public String getSecurePropertyValue(ProfileProperty var1) throws InsecureSignatureException {
                try {
                    return sessionService.getSecurePropertyValue(((ProfilePropertyImpl) var1).getInternal());
                } catch (InsecurePublicKeyException e) {
                    throw new InsecureSignatureException("[PlayerSession#getSecurePropertyValue] Insecure public key: " + e.getMessage(), e);
                }
            }
        };
    }
}
