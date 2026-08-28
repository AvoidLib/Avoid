package pl.olafcio.avoid.paper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.mixinclass.MyUnknownExecutor;
import pl.olafcio.avoid.mixinclass.Overload;
import pl.olafcio.avoid.net.command.CommandManager;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.ShouldParse;
import pl.olafcio.avoid.net.command.parameter.impl.StringParameter;
import pl.olafcio.avoid.net.player.PlayerNative;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

@ApiStatus.Internal
public class CommandSystem {
    private static final LinkedHashMap<String, CommandParameter<?>> EMPTY
                   = new LinkedHashMap<>();

    public void finishCommands(ReloadableRegistrarEvent<Commands> event) {
        CommandManager.each(cmd -> {
            var name = cmd.getName();

            var root = Commands.literal(name);
            var tree = cmd.getSyntaxTree();

            root = addNodePermissions(tree, root);

            if (tree.isNodeExecutable())
                root = root.executes(executing(tree, EMPTY, name));

            root = walk(tree, root, EMPTY, name);

            var unknownhandler = cmd.getUnknownHandler();
            if (unknownhandler != null)
                root = root.then(Commands.argument("input", StringArgumentType.greedyString())
                                         .executes(executing(new SyntaxTree(unknownhandler), new LinkedHashMap<>() {{
                                             put("input", new StringParameter("input"));
                                         }}, name)));

            event.registrar().getDispatcher().register(root);
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends ArgumentBuilder<io.papermc.paper.command.brigadier.CommandSourceStack, ?>> T walk(SyntaxTree tree, T root, LinkedHashMap<String, CommandParameter<?>> stack, String cmdName) {
        for (var entry : tree.entrySet()) {
            var node = Commands.argument(entry.getKey().getName(), StringArgumentType.word());
            var entryStack = (LinkedHashMap<String, CommandParameter<?>>) stack.clone();

            entryStack.put(entry.getKey().getName(), entry.getKey());

            node = addNodePermissions(entry.getValue(), node);

            if (entry.getValue().isNodeExecutable()) {
                node = node.suggests((ctx, builder) -> {
                    var suggestions = entry.getKey().tabcomplete();
                    if (suggestions != null)
                        for (var sug : suggestions)
                            if (sug.startsWith(builder.getRemaining()))
                                builder.suggest(sug);

                    return CompletableFuture.completedFuture(builder.build());
                });

                node = node.executes(executing(entry.getValue(), entryStack, cmdName));
            }

            walk(entry.getValue(), node, entryStack, cmdName);

            root = (T) root.then(node);
        }

        return root;
    }

    private static <T extends ArgumentBuilder<io.papermc.paper.command.brigadier.CommandSourceStack, T>> T addNodePermissions(SyntaxTree entry, T node) {
        var perm = entry.getPermission();
        if (perm != null) {
            if (perm instanceof pl.olafcio.avoid.net.command.annotation.Permission cast) {
                node = node.requires(ctx -> {
                    if (!(ctx.getSender() instanceof Player))
                        return true;

                    return ctx.getSender().hasPermission(cast.value());
                });
            } else if (perm instanceof PermissionLevel cast) {
                var cmdLevel = cast.level().__get().id();

                node = node.requires(ctx -> {
                    if (!(ctx.getSender() instanceof Player))
                        return true;

                    return ctx.getSender().hasPermission(cast.value()) ||
                           (cmdLevel == 0 || ctx.getSender().isOp() || (cmdLevel <= 2 && ctx.getSender() instanceof CommandBlock));
                });
            } else {
                throw new RuntimeException("Invalid command permission");
            }
        }

        return node;
    }

    private final HashMap<String, ArrayList<Overload>> executioners
            = new HashMap<>();

    @SuppressWarnings("unchecked")
    private Command<io.papermc.paper.command.brigadier.CommandSourceStack> executing(SyntaxTree tree, LinkedHashMap<String, CommandParameter<?>> mappings, String cmdName) {
        final var overloads = executioners.computeIfAbsent(cmdName + ";" + mappings.size(), x -> new ArrayList<>());
        final Overload callback = new Overload();

        callback.load = (ctx, repeat) -> {
            Field F_arguments;

            try {
                F_arguments = CommandContext.class.getDeclaredField("arguments");
                F_arguments.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("AvoidLib failed to reflectively retrieve CommandContext#arguments", e);
            }

            Map<String, ParsedArgument<io.papermc.paper.command.brigadier.CommandSourceStack, ?>> argsraw;

            try {
                argsraw = (Map<String, ParsedArgument<io.papermc.paper.command.brigadier.CommandSourceStack, ?>>) F_arguments.get(ctx);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("AvoidLib failed to reflectively get CommandContext#arguments", e);
            }

            HashMap<?, Object> args = new HashMap<>(argsraw);
            List<String> warn;

            if (overloads.size() > 1) {
                warn = new ArrayList<>();

                for (var key : args.keySet()) {
                    var shouldparse = mappings.get(key).shouldParse();
                    if (shouldparse == ShouldParse.YES)
                        warn.add((String) key);
                    else if (shouldparse == null)
                        Avoid.LOGGER.warn("CommandParameter#shouldParse() shouldn't return 'null'");
                }
            } else
                warn = List.of();

            Executor executor;

            var source = ctx.getSource();
            if (source.getSender() instanceof Player player) {
                executor = PlayerNative.convertFrom(Util.convert(player));
            } else {
                executor = new MyUnknownExecutor(source);
            }

            for (var entry : args.entrySet()) {
                var key = entry.getKey();
                var arg = entry.getValue();

                CommandParameter<?> param = mappings.get(key);

                try {
                    entry.setValue(param.parse((String) ((ParsedArgument<io.papermc.paper.command.brigadier.CommandSourceStack, ?>) arg).getResult()));
                } catch (CommandSyntaxException e) {
                    if (!warn.contains(key)) {
                        if (repeat)
                            return 0;

                        // Overload
                        for (var ov : overloads) {
                            if (ov != callback) {
                                var orig = (LinkedHashMap<String, ParsedArgument<io.papermc.paper.command.brigadier.CommandSourceStack, ?>>) argsraw;
                                var rekeyed = new LinkedHashMap<String, ParsedArgument<CommandSourceStack, ?>>();

                                List<String> keys = ov.mappings.sequencedKeySet().stream().toList();

                                int i = 0;
                                for (var val : orig.sequencedValues())
                                    rekeyed.put(keys.get(i++), val);

                                try {
                                    F_arguments.set(ctx, rekeyed);
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException("AvoidLib failed to reflectively set CommandContext#arguments", ex);
                                }

                                if (ov.load.apply(ctx, true) == 2)
                                    return SINGLE_SUCCESS;
                            }
                        }
                    }

                    tree.cmd.sendSyntaxException(executor, ctx, param);

                    return 2;
                }
            }

            var usage = new Usage((Map<String, Object>) args, executor);

            tree.method.run(usage);

            return 2;
        };

        callback.execute = ctx -> {
            callback.load.apply(ctx, false);
            return SINGLE_SUCCESS;
        };

        callback.mappings = mappings;

        overloads.add(callback);

        return callback.execute;
    }
}
