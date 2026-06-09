package pl.olafcio.avoid.mixin;

import com.google.common.collect.Maps;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mixin.accessors.ICommandContext;
import pl.olafcio.avoid.mixin.accessors.ICommandManager;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.command.handling.Usage;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.impl.StringParameter;

import java.util.HashMap;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

@Mixin(Commands.class)
public class CommandsMixin {
    @Shadow
    @Final
    private CommandDispatcher<CommandSourceStack> dispatcher;

    @Unique
    private static final HashMap<String, CommandParameter<?>> EMPTY
                   = new HashMap<>();

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"), method = "<init>")
    public void finishCommands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci) {
        ICommandManager.avoid$each(cmd -> {
            var root = Commands.literal(cmd.getName());
            var tree = cmd.getSyntaxTree();

            if (tree.isNodeExecutable())
                root = root.executes(executing(tree, EMPTY));

            root = walk(tree, root, EMPTY);

            var unknownhandler = cmd.getUnknownHandler();
            if (unknownhandler != null)
                root = root.then(Commands.argument("input", StringArgumentType.greedyString())
                                         .executes(executing(new SyntaxTree(unknownhandler), new HashMap<>() {{
                                             put("rest", new StringParameter("rest"));
                                         }})));

            this.dispatcher.register(root);
        });
    }

    @Unique
    @SuppressWarnings("unchecked")
    private <T extends ArgumentBuilder<CommandSourceStack, ?>> T walk(SyntaxTree tree, T root, HashMap<String, CommandParameter<?>> stack) {
        for (var entry : tree.entrySet()) {
            var node = Commands.argument(entry.getKey().getName(), StringArgumentType.word());
            var entryStack = (HashMap<String, CommandParameter<?>>) stack.clone();

            entryStack.put(entry.getKey().getName(), entry.getKey());

            if (entry.getValue().isNodeExecutable()) {
                node = node.executes(executing(entry.getValue(), entryStack));
            }

            walk(entry.getValue(), node, stack);

            root = (T) root.then(node);
        }

        return root;
    }

    @Unique
    @SuppressWarnings("unchecked")
    private Command<CommandSourceStack> executing(SyntaxTree tree, HashMap<String, CommandParameter<?>> mappings) {
        return ctx -> {
            var args = ((ICommandContext<CommandSourceStack>) ctx).avoid$arguments();
            var usage = new Usage(Maps.transformEntries(args, (key, arg) -> {
                return mappings.get(key).parse((String) arg.getResult());
            }));

            tree.method.run(usage);

            return SINGLE_SUCCESS;
        };
    }
}
