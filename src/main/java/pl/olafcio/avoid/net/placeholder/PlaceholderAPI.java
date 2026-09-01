package pl.olafcio.avoid.net.placeholder;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.player.Player;

@ApiStatus.Experimental
public final class PlaceholderAPI {
    @ApiStatus.Internal
    private PlaceholderAPI() {}

    @ServerOnly
    public static BaseComponent<?> replacePlaceholders_ServerOnly(BaseComponent<?> message) {
        if (AvoidInternal.getServer() == null)
            throw new ImproperEnvironment("[PlaceholderAPI#replacePlaceholders_ServerOnly] Can be invoked only from the server");

        if (!AvoidWrappedLoader.isModPresent("placeholder-api"))
            return message;

        return COFromNative.from(Placeholders.parseText(
                COToNative.from(message),
                PlaceholderContext.of(AvoidInternal.getServer())
        ));
    }

    public static BaseComponent<?> replacePlaceholders(BaseComponent<?> message, @NotNull Player player) {
        if (!AvoidWrappedLoader.isModPresent("placeholder-api"))
            return message;

        PlaceholderContext ctx;

        var entity = EntityNative.convert(player);
        if (entity instanceof ServerPlayer sp)
            ctx = PlaceholderContext.of(sp);
        else
            ctx = PlaceholderContext.of(entity);

        return COFromNative.from(Placeholders.parseText(
                COToNative.from(message),
                ctx
        ));
    }
}
