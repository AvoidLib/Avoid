package pl.olafcio.avoid.net.player;

import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class PlayerNative {
    @ApiStatus.Internal
    private PlayerNative() {}

    public static Player convertFrom(net.minecraft.world.entity.player.Player player) {
        var profile = player.getGameProfile();

        return new Player(
                player.getId(),
                EntityTypeNative.convertFrom(player.getType()),
                Vect3Native.convert(player.position()),
                Vect3Native.convert(player.getDeltaMovement()),
                player.getUUID(),
                player.getStringUUID(),
                COFromNative.from(player.getName()),
                new PlayerProfile(profile.id(), profile.name(), new HashMap<>() {{
                    var map = profile.properties().asMap();
                    this.putAll(map);
                }}),
                player instanceof ServerPlayer sp ? sp.connection : null,
                player
        );
    }
}
