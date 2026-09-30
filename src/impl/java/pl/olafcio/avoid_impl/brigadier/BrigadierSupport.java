package pl.olafcio.avoid_impl.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.net.command.CommandManager;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.ShouldParse;
import pl.olafcio.avoid.net.command.parameter.impl.LiteralParameter;
import pl.olafcio.avoid.net.command.parameter.impl.StringParameter;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

@ApiStatus.Internal
public abstract class BrigadierSupport<Src> {
    static final LinkedHashMap<String, CommandParameter<?>> EMPTY
           = new LinkedHashMap<>();

    public void finishCommands(CommandDispatcher<Src> dispatcher) {
        CommandManager.each(cmd -> {
            var name = cmd.getName();

            var root = LiteralArgumentBuilder.<Src>literal(name);
            var tree = cmd.getSyntaxTree();

            root = addNodePermissions(tree, root);

            if (tree.isNodeExecutable())
                root = root.executes(executing(tree, EMPTY, name));

            root = walk(tree, root, EMPTY, name);

            var unknownhandler = cmd.getUnknownHandler();
            if (unknownhandler != null)
                root = root.then(RequiredArgumentBuilder.<Src, String>argument("input", StringArgumentType.greedyString())
                           .executes(executing(new SyntaxTree(unknownhandler), new LinkedHashMap<>() {{
                                put("input", new StringParameter("input"));
                           }}, name)));

            dispatcher.register(root);
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends ArgumentBuilder<Src, T>> T addNodePermissions_unsafe(SyntaxTree entry, ArgumentBuilder<Src, ?> obj) {
        return addNodePermissions(entry, (T) obj);
    }

    @SuppressWarnings("unchecked")
    private <T extends ArgumentBuilder<Src, ?>> T walk(SyntaxTree tree, T root, LinkedHashMap<String, CommandParameter<?>> stack, String cmdName) {
        for (var entry : tree.entrySet()) {
            var node =

                    entry.getKey() instanceof LiteralParameter lp

                            ? LiteralArgumentBuilder.<Src>literal(lp.getValue())
                            : RequiredArgumentBuilder.<Src, String>argument(
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

                node = (ArgumentBuilder<Src, ?>) rab.suggests((ctx, builder) -> {
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

    protected abstract <T extends ArgumentBuilder<Src, T>> T addNodePermissions(SyntaxTree entry, T node);

    private final HashMap<String, ArrayList<Overload<Src>>> executioners
            = new HashMap<>();

    @SuppressWarnings("unchecked")
    private Command<Src> executing(SyntaxTree tree, LinkedHashMap<String, CommandParameter<?>> mappings, String cmdName) {
        final var overloads = executioners.computeIfAbsent(cmdName + ";" + mappings.size(), x -> new ArrayList<>());
        final Overload<Src> callback = new Overload<>();

        callback.load = (ctx, repeat) -> {
            var ictx = (ICommandContext<Src>) ctx;
            var argsraw = ictx.avoid$arguments();

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

            Executor executor = getExecutor(ctx);

            for (var entry : args.entrySet()) {
                var key = entry.getKey();
                var arg = entry.getValue();

                CommandParameter<?> param = mappings.get(key);

                try {
                    entry.setValue(param.parse((String) ((ParsedArgument<Src, ?>) arg).getResult()));
                } catch (CommandSyntaxException e) {
                    if (!warn.contains(key)) {
                        if (repeat)
                            return 0;

                        // Overload
                        for (var ov : overloads) {
                            if (ov != callback) {
                                var orig = (LinkedHashMap<String, ParsedArgument<Src, ?>>) argsraw;
                                var rekeyed = new LinkedHashMap<String, ParsedArgument<Src, ?>>();

                                List<String> keys = ov.mappings.sequencedKeySet().stream().toList();

                                int i = 0;
                                for (var val : orig.sequencedValues())
                                    rekeyed.put(keys.get(i++), val);

                                ictx.avoid$arguments(rekeyed);

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

    protected abstract Executor getExecutor(CommandContext<Src> ctx);
}
