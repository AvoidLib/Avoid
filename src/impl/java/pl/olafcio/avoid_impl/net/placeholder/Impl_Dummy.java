package pl.olafcio.avoid_impl.net.placeholder;

import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.placeholder.PlaceholderProvider;
import pl.olafcio.avoid.net.player.Player;

/**
 * A no-op implementation for {@linkplain PlaceholderProvider}.
 */
@NullMarked
final class Impl_Dummy implements PlaceholderProvider {
    @Override
    public BaseComponent<?> replaceServerPlaceholders(BaseComponent<?> message) {
        return message;
    }

    @Override
    public BaseComponent<?> replacePlaceholders(BaseComponent<?> message, Player player) {
        return message;
    }
}
