package pl.olafcio.avoid_impl.mixin;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.StringArgument;
import net.minecraft.commands.suggestions.ServerOnlyCustomSuggestion;
import net.minecraft.commands.suggestions.SuggestionContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.server.permissions.PermissionProviderCheck;
import net.minecraft.server.permissions.PermissionSet;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid_impl.brigadier.BrigadierSupport;
import pl.olafcio.avoid_impl.mixin.accessors.ICommandContext;
import pl.olafcio.avoid_impl.mixinclass.MyUnknownExecutor;
import org.spongepowered.asm.mixin.injection.Coerce;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.command_server.event.ServerCommandExecuteEvent;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.player.PlayerNative;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static pl.olafcio.avoid_impl.net.permission.PermissionHandlers.createAtom;

@Mixin(Commands.class)
public class CommandsMixin {
    //@Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(at = @At("HEAD"), method = "performCommand", cancellable = true)
    private static void performCommand(CommandSourceStack executionContext, @Coerce Object command, String string, CallbackInfo ci) {
        var event = new ServerCommandExecuteEvent(string);

        EventManager.fire(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"), method = "<init>")
    public void finishCommands(
        Consumer<CommandDispatcher<CommandSourceStack>> commandRegistration,
        CallbackInfo ci,
        @Local(name = "dispatcher") CommandDispatcher<CommandSourceStack> dispatcher
) {
        new BrigadierSupport<CommandSourceStack>() {
            @Override
            protected <T extends ArgumentBuilder<CommandSourceStack, T>> T addNodePermissions(SyntaxTree entry, T node) {
                var perm = entry.getPermission();
                if (perm != null) {
                    if (perm instanceof pl.olafcio.avoid.net.command.annotation.Permission cast) {
                        var atom = createAtom(cast.value());

                        node = node.requires(new PermissionProviderCheck<>(new PermissionCheck.Require(atom)));
                    } else if (perm instanceof PermissionLevel cast) {
                        var atom = createAtom(cast.value());
                        var cmdLevel = new Permission.HasCommandLevel(cast.level().__get());

                        node = node.requires(new PermissionProviderCheck<>(new PermissionCheck.Require(atom) {
                            @Override
                            public boolean check(PermissionSet perms) {
                                return perms.avoid$hasPermission(atom, cmdLevel);
                            }
                        }));
                    } else {
                        throw new RuntimeException("Invalid command permission");
                    }
                }

                return node;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected Map<String, ParsedArgument<CommandSourceStack, ?>> getArguments(CommandContext<CommandSourceStack> ctx) {
                return ((ICommandContext<CommandSourceStack>) ctx).avoid$arguments();
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void setArguments(CommandContext<CommandSourceStack> ctx, LinkedHashMap<String, ParsedArgument<CommandSourceStack, ?>> rekeyed) {
                ((ICommandContext<CommandSourceStack>) ctx).avoid$arguments(rekeyed);
            }

            @Override
            protected Executor getExecutor(CommandContext<CommandSourceStack> ctx) {
                var source = ctx.getSource();
                if (source.getPlayer() instanceof ServerPlayer player) {
                    return PlayerNative.convertFrom(player);
                } else {
                    return new MyUnknownExecutor(source);
                }
            }

            @Override
            @NotNull
            protected SuggestionProvider<CommandSourceStack> getSuggestionProvider(Function<CommandContext<?>, String[]> suggester) {
                return new MyServerOnlyCustomSuggestion(suggester).toBrigadier();
            }

            @Override
            @NotNull
            protected ArgumentType<String> provideGreedyString() {
                return StringArgument.greedyString();
            }

            @Override
            @NotNull
            protected ArgumentType<String> provideWordString() {
                return StringArgument.word();
            }
        }.finishCommands(dispatcher);
    }

    private static class MyServerOnlyCustomSuggestion implements ServerOnlyCustomSuggestion {
        private final Function<CommandContext<?>, String[]> suggester;

        public MyServerOnlyCustomSuggestion(Function<CommandContext<?>, String[]> suggester) {
            this.suggester = suggester;
        }

        @Override
        public CompletableFuture<Suggestions> generate(MinecraftServer server, CommandContext<? extends SuggestionContext> ctx, SuggestionsBuilder builder) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
            return BrigadierSupport.performActualSuggestions(ctx, builder, suggester);
        }
    }
}
