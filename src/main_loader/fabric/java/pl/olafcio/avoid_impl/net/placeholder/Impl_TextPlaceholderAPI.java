package pl.olafcio.avoid_impl.net.placeholder;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.placeholder.PlaceholderProvider;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid_impl.net.entity.EntityNative;

/**
 * Implementation using the <a href="https://modrinth.com/plugin/placeholder-api">Text Placeholder API</a>.
 */
@NullMarked
public class Impl_TextPlaceholderAPI implements PlaceholderProvider {
    @Override
    public BaseComponent<?> replaceServerPlaceholders(BaseComponent<?> message) {
        return COFromNative.from(Placeholders.parseText(
                COToNative.from(message),
                PlaceholderContext.of(AvoidInternal.getServer())
        ));
    }

    @Override
    public BaseComponent<?> replacePlaceholders(BaseComponent<?> message, Player player) {
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
