package pl.olafcio.avoid_impl.paper;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid_impl.brigadier.BrigadierSupport;

import java.lang.reflect.Field;
import java.util.*;

@ApiStatus.Internal
public class CommandSystem {
    public void finishCommands(ReloadableRegistrarEvent<Commands> event) {
        new BrigadierSupport<CommandSourceStack>() {
            protected <T extends ArgumentBuilder<CommandSourceStack, T>> T addNodePermissions(SyntaxTree entry, T node) {
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

            protected Map<String, ParsedArgument<CommandSourceStack, ?>> getArguments(CommandContext<CommandSourceStack> ctx) {
                try {
                    return (Map<String, ParsedArgument<CommandSourceStack, ?>>) argsfield().get(ctx);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("AvoidLib failed to reflectively get CommandContext#arguments", e);
                }
            }

            @Override
            protected Executor getExecutor(CommandContext<CommandSourceStack> ctx) {
                var source = ctx.getSource();
                if (source.getSender() instanceof Player player) {
                    return PlayerNative.convertFrom(Util.convert(player));
                } else {
                    return new UnknownExecutor(source);
                }
            }

            protected void setArguments(CommandContext<CommandSourceStack> ctx, LinkedHashMap<String, ParsedArgument<CommandSourceStack, ?>> rekeyed) {
                try {
                    argsfield().set(ctx, rekeyed);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException("AvoidLib failed to reflectively set CommandContext#arguments", ex);
                }
            }

            private Field argsfield() {
                Field F_arguments;

                try {
                    F_arguments = CommandContext.class.getDeclaredField("arguments");
                    F_arguments.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException("AvoidLib failed to reflectively retrieve CommandContext#arguments", e);
                }

                return F_arguments;
            }
        }.finishCommands(event.registrar().getDispatcher());
    }
}
