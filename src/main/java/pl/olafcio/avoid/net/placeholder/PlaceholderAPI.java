package pl.olafcio.avoid.net.placeholder;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.errors.ImproperEnvironment;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid_impl.net.entity.EntityNative;
import pl.olafcio.avoid.net.player.Player;

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
 * On other platforms, the method implementations are no-op.
 */
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
