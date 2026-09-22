package pl.olafcio.avoid.net.placeholder;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class PlaceholderAPI {
    @ApiStatus.Internal
    private PlaceholderAPI() {}

    @ServerOnly
    public static BaseComponent<?> replacePlaceholders_ServerOnly(BaseComponent<?> message) {
        if (AvoidInternal.getServer() == null)
            throw new ImproperEnvironment("[PlaceholderAPI#replacePlaceholders_ServerOnly] Can be invoked only from the server");

        return message;
    }

    public static BaseComponent<?> replacePlaceholders(BaseComponent<?> message, @NotNull Player player) {
        return message;
    }
}
