package pl.olafcio.avoid.net.placeholder;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid.errors.ImproperEnvironment;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid_impl.net.placeholder.DefaultPlaceholders;

import java.util.*;

/**
 * A namespace used to retrieve (and soon, create) placeholders.
 * <br/><br/>
 * A placeholder is a text fragment, usually configurable by the user, that will be replaced with a dynamic value.<br/><br/>
 * For example:
 * <ul>
 *     <li>{@code The TPS: %server_tps%}</li>
 *     <li>{@code Player: %player_name%}</li>
 * </ul>
 * <br/>
 * This class is currently dependent on other placeholder systems.<br/>
 * Here's a table of them:
 * <table cellspacing="8" style="border: 1px solid green; margin-top: 7.7px">
 *     <tr style="background:green;color:black">
 *         <th>Platform</th>
 *         <th>System</th>
 *     </tr>
 *     <tr>
 *         <td style="border-right: 1px solid green">Paper</td>
 *         <td><a href="https://modrinth.com/plugin/placeholderapi">Placeholder API</a></td>
 *     </tr>
 *     <tr>
 *         <td style="border-right: 1px solid green">Fabric</td>
 *         <td><a href="https://modrinth.com/plugin/placeholder-api">Text Placeholder API</a></td>
 *     </tr>
 * </table>
 * <br/>
 * On other platforms, the default method implementations are no-op.
 * <br/><br/>
 * You can implement this API yourself by constructing a {@linkplain PlaceholderProvider} and passing it to {@linkplain Service#setInstance(PlaceholderProvider) Service.setInstance(...)}.
 */
@NeverRemoval
public final class PlaceholderAPI {
    @ApiStatus.Internal
    private PlaceholderAPI() {}

    private static PlaceholderProvider instance
                 = DefaultPlaceholders.get();

    private static final Collection<PlaceholderProvider> instances
                   = new HashSet<>();

    /**
     * A subclass for managing the {@linkplain PlaceholderProvider} implementation.
     */
    public enum Service {
        ;

        /**
         * Returns the current {@linkplain PlaceholderProvider} implementation.
         */
        public static PlaceholderProvider getInstance() {
            return PlaceholderAPI.instance;
        }

        /**
         * Returns all overridden, along with the current, {@linkplain PlaceholderProvider} implementations.
         */
        public static Collection<PlaceholderProvider> getAllInstances() {
            return PlaceholderAPI.instances;
        }

        /**
         * Sets the current {@linkplain PlaceholderProvider} implementation.
         */
        public static void setInstance(PlaceholderProvider instance) {
            PlaceholderAPI.instance = instance;
            PlaceholderAPI.instances.add(instance);
        }
    }

    /**
     * @deprecated Use {@link #replaceServerPlaceholders(BaseComponent)} instead.
     */
    @ServerOnly
    @Deprecated(forRemoval = true, since = "v2.1")
    public static BaseComponent<?> replacePlaceholders_ServerOnly(BaseComponent<?> message) {
        return replaceServerPlaceholders(message);
    }

    /**
     * Replaces server-only placeholders in the message.
     * @param message The message to expand.
     */
    @ServerOnly
    public static BaseComponent<?> replaceServerPlaceholders(BaseComponent<?> message) {
        if (AvoidInternal.getServer() == null)
            throw new ImproperEnvironment("[PlaceholderAPI#replaceServerPlaceholders] Can be invoked only from the server");

        return instance.replaceServerPlaceholders(message);
    }

    /**
     * Replaces placeholders for a player in the message.
     * @param message The message to expand.
     * @param player The player to specify for placeholders.
     */
    public static BaseComponent<?> replacePlaceholders(BaseComponent<?> message, @NotNull Player player) {
        return instance.replacePlaceholders(message, player);
    }
}
