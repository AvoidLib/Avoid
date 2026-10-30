package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.command.external.ExternalCommand;
import pl.olafcio.avoid.net.command.external.PlayerNotInWorldException;

/**
 * A namespace for managing client commands.
 * <br/><br/>
 * To register a command, use {@link #add(ClientCommand)}.<br/>
 * To get a command by its name, use {@link #getByName(String)}.
 */
@WillRefactor(aspect = "name")
public final class ClientCommandManager {
    @ApiStatus.Internal
    private ClientCommandManager() {}

    private static final CommandManager.Instance<ClientCommand> INSTANCE
                   = new CommandManager.Instance<>() {
                        @Override
                        public @Nullable ExternalCommand getByName(String name) throws PlayerNotInWorldException {
                            return pl.olafcio.avoid_impl.net.command.CommandLookup.queryClient(name);
                        }
                    };

    public static CommandManager.Instance<ClientCommand> getInstance() {
        return INSTANCE;
    }

    public static void add(ClientCommand cmd) {
        INSTANCE.add(cmd);
    }

    @Nullable
    public static ExternalCommand getByName(String name) throws PlayerNotInWorldException {
        return INSTANCE.getByName(name);
    }

    static CommandMetadata get(ClientCommand cmd) {
        return INSTANCE.get(cmd);
    }
}
