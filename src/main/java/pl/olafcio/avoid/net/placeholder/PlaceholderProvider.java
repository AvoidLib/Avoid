package pl.olafcio.avoid.net.placeholder;

import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.Player;

/**
 * An implementation for the {@linkplain PlaceholderAPI} class.
 */
@NullMarked
public interface PlaceholderProvider {
    /**
     * Replaces server-only placeholders in the message.
     * @param message The message to expand.
     */
    @ServerOnly
    BaseComponent<?> replaceServerPlaceholders(BaseComponent<?> message);

    /**
     * Replaces placeholders for a player in the message.
     * @param message The message to expand.
     * @param player The player to specify for placeholders.
     */
    BaseComponent<?> replacePlaceholders(BaseComponent<?> message, Player player);
}
