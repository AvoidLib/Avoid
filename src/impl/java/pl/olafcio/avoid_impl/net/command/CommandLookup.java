package pl.olafcio.avoid_impl.net.command;

import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import pl.olafcio.avoid.net.client.Client;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.external.ClientCommand;
import pl.olafcio.avoid.net.command.external.ExtendedExecutorException;
import pl.olafcio.avoid.net.command.external.ExternalCommand;
import pl.olafcio.avoid.net.command.external.PlayerNotInWorldException;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.exception.UncontrollablePlayerException;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid_impl.mixinclass.MyUnknownExecutor;
import pl.olafcio.avoid_impl.net.entity.EntityNative;

import java.util.Collections;
import java.util.function.Predicate;

public final class CommandLookup {
    private CommandLookup() {}

    abstract static class ExternalCommandImpl<S> implements ExternalCommand {
        protected final CommandNode<S> command;

        ExternalCommandImpl(CommandNode<S> command) {
            this.command = command;
        }

        @Override
        public String getName() {
            return command.getName();
        }

        @Override
        public String getUsage() {
            return command.getUsageText();
        }

        @Override
        public String[] getExamples() {
            return command.getExamples().toArray(String[]::new);
        }
    }

    abstract static class ClientCommandImpl<S> extends ExternalCommandImpl<S> implements ClientCommand {
        ClientCommandImpl(CommandNode<S> command) {
            super(command);
        }
    }

    public static ClientCommand queryClient(String name) {
        var player = Minecraft.getInstance().player;
        if (player == null)
            throw new PlayerNotInWorldException("[CommandManager#getByName] The player needs to be in a world");

        var dispatcher = player.connection.getCommands();
        var command = dispatcher.findNode(Collections.singleton(name));

        if (command == null)
            return null;

        return new ClientCommandImpl<>(command) {
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

    public static ExternalCommand queryServer(String name) {
        var server = AvoidInternal.getServer();

        var dispatcher = server.getCommands().getDispatcher();
        var command = dispatcher.findNode(Collections.singleton(name));

        if (command == null)
            return null;

        return new ExternalCommandImpl<>(command) {
            @Override
            public Predicate<Executor> getRequirement() {
                var requirement = command.getRequirement();

                return source -> {
                    CommandSourceStack stack;

                    if (source instanceof Player player) {
                        var user = (ServerPlayer) EntityNative.convert(player);

                        stack = new CommandSourceStack(
                                CommandSource.NULL,
                                user.position(),
                                user.getRotationVector(),
                                user.level(),
                                user.permissions(),
                                user.getPlainTextName(),
                                user.getDisplayName(),
                                user.level().getServer(),
                                user
                        );
                    } else if (source instanceof MyUnknownExecutor mue) {
                        stack = mue.source;
                    } else {
                        throw new ExtendedExecutorException("[ExternalCommand#getRequirement] Cannot convert custom unknown executor");
                    }

                    return requirement.test(stack);
                };
            }
        };
    }
}
