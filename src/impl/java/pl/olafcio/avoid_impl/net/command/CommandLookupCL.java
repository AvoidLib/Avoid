package pl.olafcio.avoid_impl.net.command;

import net.minecraft.client.Minecraft;
import pl.olafcio.avoid.net.client.Client;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.external.ClientCommand;
import pl.olafcio.avoid.net.command.external.PlayerNotInWorldException;
import pl.olafcio.avoid.net.player.exception.UncontrollablePlayerException;

import java.util.Collections;

public final class CommandLookupCL {
    private CommandLookupCL() {}

    public static ClientCommand queryClient(String name) {
        var player = Minecraft.getInstance().player;
        if (player == null)
            throw new PlayerNotInWorldException("[CommandManager#getByName] The player needs to be in a world");

        var dispatcher = player.connection.getCommands();
        var command = dispatcher.findNode(Collections.singleton(name));

        if (command == null)
            return null;

        return new CommandLookup.ClientCommandImpl<>(command) {
            @Override
            public Requirement getRequirement() {
                var requirement = command.getRequirement();

                return new Requirement() {
                    @Override
                    public boolean test() {
                        return requirement.test(player.connection.getSuggestionsProvider());
                    }

                    @Override
                    public boolean test(Executor executor) {
                        if (executor != Client.getPlayer())
                            throw new UncontrollablePlayerException("[ClientCommand\\Requirement#test] Non-local players cannot be used as targets for permission checks");

                        return test();
                    }
                };
            }
        };
    }
}
