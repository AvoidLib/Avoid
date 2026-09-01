package pl.olafcio.avoid.net.placeholder;

import me.clip.placeholderapi.PAPIComponents;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter_kyori.COFromNative;
import pl.olafcio.avoid.net.chat.converter_kyori.COToNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class PlaceholderAPI {
    @ApiStatus.Internal
    private PlaceholderAPI() {}

    @ServerOnly
    public static BaseComponent<?> replacePlaceholders_ServerOnly(BaseComponent<?> message) {
        if (!AvoidWrappedLoader.isModPresent("PlaceholderAPI"))
            return message;

        return COFromNative.from(PAPIComponents.setPlaceholders(
                null,
                COToNative.from(message)
        ));
    }

    public static BaseComponent<?> replacePlaceholders(BaseComponent<?> message, @NotNull Player player) {
        if (!AvoidWrappedLoader.isModPresent("PlaceholderAPI"))
            return message;

        return COFromNative.from(PAPIComponents.setPlaceholders(
                Bukkit.getPlayer(((ServerPlayer) EntityNative.convert(player)).getUUID()),
                COToNative.from(message)
        ));
    }
}
