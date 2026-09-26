package pl.olafcio.avoid_impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.net.command.Command;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.ShouldParse;
import pl.olafcio.avoid.net.command.parameter.impl.LiteralParameter;
import pl.olafcio.avoid.net.command.parameter.impl.StringParameter;
import pl.olafcio.avoid.net.player.PlayerNative;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

class BrigadierSupport {
    private static final LinkedHashMap<String, CommandParameter<?>> EMPTY
                   = new LinkedHashMap<>();

    public static LiteralCommandNode<CommandSource> create(Command cmd) {
        var name = cmd.getName();

        var root = BrigadierCommand.literalArgumentBuilder(name);
        var tree = cmd.getSyntaxTree();

        root = addNodePermissions(tree, root);

        if (tree.isNodeExecutable())
            root = root.executes(executing(tree, EMPTY, name));

        root = walk(tree, root, EMPTY, name);

        var unknownhandler = cmd.getUnknownHandler();
        if (unknownhandler != null)
            root = root.then(BrigadierCommand.requiredArgumentBuilder("input", StringArgumentType.greedyString())
                    .executes(executing(new SyntaxTree(unknownhandler), new LinkedHashMap<>() {{
                        put("input", new StringParameter("input"));
                    }}, name)));

        return root.build();
    }

    @SuppressWarnings("unchecked")
    private static <T extends ArgumentBuilder<CommandSource, T>> T addNodePermissions_unsafe(SyntaxTree entry, ArgumentBuilder<CommandSource, ?> obj) {
        return addNodePermissions(entry, (T) obj);
    }

    @SuppressWarnings("unchecked")
    private static <T extends ArgumentBuilder<CommandSource, ?>> T walk(SyntaxTree tree, T root, LinkedHashMap<String, CommandParameter<?>> stack, String cmdName) {
        for (var entry : tree.entrySet()) {
            var node =

                    entry.getKey() instanceof LiteralParameter lp

                            ? BrigadierCommand.literalArgumentBuilder(lp.getValue())
                            : BrigadierCommand.requiredArgumentBuilder(
                                    entry.getKey().getName(),
                                    StringArgumentType.word()
                            );

            var entryStack = (LinkedHashMap<String, CommandParameter<?>>) stack.clone();

            entryStack.put(entry.getKey().getName(), entry.getKey());

            node = addNodePermissions_unsafe(entry.getValue(), node);

            if (node instanceof RequiredArgumentBuilder<?,?> rab) {
                Function<CommandContext<?>, String[]> suggester = entry.getValue().tabcomplete == null

                        ? ctx -> entry.getKey().tabcomplete()
                        : ctx -> entry.getValue().tabcomplete.apply(ctx.getInput());

                node = (ArgumentBuilder<CommandSource, ?>) rab.suggests((ctx, builder) -> {
                    var suggestions = suggester.apply(ctx);
                    if (suggestions != null)
                        for (var sug : suggestions)
                            if (sug.startsWith(builder.getRemaining()))
                                builder.suggest(sug);

                    return CompletableFuture.completedFuture(builder.build());
                });
            }

            if (entry.getValue().isNodeExecutable()) {
                node = node.executes(executing(entry.getValue(), entryStack, cmdName));
            }

            walk(entry.getValue(), node, entryStack, cmdName);

            root = (T) root.then(node);
        }

        return root;
    }

    private static <T extends ArgumentBuilder<CommandSource, T>> T addNodePermissions(SyntaxTree entry, T node) {
        var perm = entry.getPermission();
        if (perm != null) {
            if (perm instanceof pl.olafcio.avoid.net.command.annotation.Permission cast) {
                node = node.requires(ctx -> {
                    return ctx.hasPermission(cast.value());
                });
            } else if (perm instanceof PermissionLevel cast) {
                node = node.requires(ctx -> {
                    return ctx.hasPermission(cast.value());
                });
            } else {
                throw new RuntimeException("Invalid command permission");
            }
        }

        return node;
    }

    private static final HashMap<String, ArrayList<Overload>> executioners
                   = new HashMap<>();

    @SuppressWarnings("unchecked")
    private static com.mojang.brigadier.Command<CommandSource> executing(SyntaxTree tree, LinkedHashMap<String, CommandParameter<?>> mappings, String cmdName) {
        final var overloads = executioners.computeIfAbsent(cmdName + ";" + mappings.size(), x -> new ArrayList<>());
        final Overload callback = new Overload();

        callback.load = (ctx, repeat) -> {
            var argsraw = ctx.getArguments();

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
            if (source instanceof Player player) {
                executor = PlayerNative.convertFrom(player);
            } else {
                executor = new MyUnknownExecutor(source);
            }

            for (var entry : args.entrySet()) {
                var key = entry.getKey();
                var arg = entry.getValue();

                CommandParameter<?> param = mappings.get(key);

                try {
                    entry.setValue(param.parse((String) ((ParsedArgument<CommandSource, ?>) arg).getResult()));
                } catch (CommandSyntaxException e) {
                    if (!warn.contains(key)) {
                        if (repeat)
                            return 0;

                        // Overload
                        for (var ov : overloads) {
                            if (ov != callback) {
                                var orig = (LinkedHashMap<String, ParsedArgument<CommandSource, ?>>) argsraw;
                                var rekeyed = new LinkedHashMap<String, ParsedArgument<CommandSource, ?>>();

                                List<String> keys = ov.mappings.sequencedKeySet().stream().toList();

                                int i = 0;
                                for (var val : orig.sequencedValues())
                                    rekeyed.put(keys.get(i++), val);

                                Reflect.set(CommandContext.class, "arguments", ctx, rekeyed);

                                if (ov.load.apply(ctx, true) == 2)
                                    return SINGLE_SUCCESS;
                            }
                        }
                    }

                    tree.cmd.sendSyntaxException(executor, new Usage((Map<String, Object>) args, executor), param);

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
