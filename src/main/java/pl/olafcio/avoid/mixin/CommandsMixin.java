package pl.olafcio.avoid.mixin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mixin.accessors.ICommandContext;
import pl.olafcio.avoid.mixin.accessors.ICommandManager;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.command.exception.use.CommandSyntaxException;
import pl.olafcio.avoid.net.command.executor.UnknownExecutor;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.ShouldParse;
import pl.olafcio.avoid.net.command.parameter.impl.StringParameter;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.player.PlayerProfile;
import pl.olafcio.avoid.net.world.Vect3Native;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

@Mixin(Commands.class)
public class CommandsMixin {
    @Shadow
    @Final
    private CommandDispatcher<CommandSourceStack> dispatcher;

    @Unique
    private static final LinkedHashMap<String, CommandParameter<?>> EMPTY
                   = new LinkedHashMap<>();

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"), method = "<init>")
    public void finishCommands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci) {
        ICommandManager.avoid$each(cmd -> {
            var name = cmd.getName();

            var root = Commands.literal(name);
            var tree = cmd.getSyntaxTree();

            if (tree.isNodeExecutable())
                root = root.executes(executing(tree, EMPTY, name));

            root = walk(tree, root, EMPTY, name);

            var unknownhandler = cmd.getUnknownHandler();
            if (unknownhandler != null)
                root = root.then(Commands.argument("input", StringArgumentType.greedyString())
                                         .executes(executing(new SyntaxTree(unknownhandler), new LinkedHashMap<>() {{
                                             put("input", new StringParameter("input"));
                                         }}, name)));

            this.dispatcher.register(root);
        });
    }

    @Unique
    @SuppressWarnings("unchecked")
    private <T extends ArgumentBuilder<CommandSourceStack, ?>> T walk(SyntaxTree tree, T root, LinkedHashMap<String, CommandParameter<?>> stack, String cmdName) {
        for (var entry : tree.entrySet()) {
            var node = Commands.argument(entry.getKey().getName(), StringArgumentType.word());
            var entryStack = (LinkedHashMap<String, CommandParameter<?>>) stack.clone();

            entryStack.put(entry.getKey().getName(), entry.getKey());

            if (entry.getValue().isNodeExecutable()) {
                node = node.suggests((ctx, builder) -> {
                    var suggestions = entry.getKey().tabcomplete();
                    if (suggestions != null)
                        for (var sug : suggestions)
                            if (builder.getInput().startsWith(sug))
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

    @Unique
    private static final class Overload {
        public BiFunction<CommandContext<CommandSourceStack>, Boolean, Integer> load;
        public Command<CommandSourceStack> execute;
        public LinkedHashMap<String, CommandParameter<?>> mappings;
    }

    private final HashMap<String, ArrayList<Overload>> executioners
            = new HashMap<>();

    @Unique
    @SuppressWarnings("unchecked")
    private Command<CommandSourceStack> executing(SyntaxTree tree, LinkedHashMap<String, CommandParameter<?>> mappings, String cmdName) {
        final var overloads = executioners.computeIfAbsent(cmdName + ";" + mappings.size(), x -> new ArrayList<>());
        final Overload callback = new Overload();

        callback.load = (ctx, repeat) -> {
            var ictx = (ICommandContext<CommandSourceStack>) ctx;
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

            Executor executor = null;

            for (var entry : args.entrySet()) {
                var key = entry.getKey();
                var arg = entry.getValue();

                var source = ctx.getSource();
                if (source.getPlayer() instanceof ServerPlayer player) {
                    var profile = player.getGameProfile();

                    executor = new pl.olafcio.avoid.net.player.Player(
                            player.getId(),
                            EntityTypeNative.convertFrom(player.getType()),
                            Vect3Native.convert(player.position()),
                            player.getUUID(),
                            player.getStringUUID(),
                            COFromNative.from(player.getName()),
                            new PlayerProfile(profile.id(), profile.name(), new HashMap<>() {{
                                var map = profile.properties().asMap();
                                this.putAll(map);
                            }}),
                            player.connection
                    );
                } else {
                    executor = new UnknownExecutor() {
                        @Override
                        public void sendMessage(BaseComponent<?> component) {
                            source.sendSystemMessage(COToNative.from(component));
                        }
                    };
                }

                try {
                    entry.setValue(mappings.get(key).parse((String) ((ParsedArgument<CommandSourceStack, ?>) arg).getResult()));
                } catch (CommandSyntaxException e) {
                    if (!warn.contains(key)) {
                        if (repeat)
                            return 0;

                        // Overload
                        for (var ov : overloads) {
                            if (ov != callback) {
                                var orig = (LinkedHashMap<String, ParsedArgument<CommandSourceStack, ?>>) argsraw;
                                var rekeyed = new LinkedHashMap<String, ParsedArgument<CommandSourceStack, ?>>();

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

                    tree.cmd.sendSyntaxException(executor, ctx);

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
