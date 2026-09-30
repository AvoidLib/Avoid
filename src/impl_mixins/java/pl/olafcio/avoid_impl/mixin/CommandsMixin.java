package pl.olafcio.avoid_impl.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid_impl.brigadier.BrigadierSupport;
import pl.olafcio.avoid_impl.mixinclass.MyUnknownExecutor;
import org.spongepowered.asm.mixin.injection.Coerce;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.command_server.event.ServerCommandExecuteEvent;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.command.SyntaxTree;
import pl.olafcio.avoid.net.player.PlayerNative;

@Mixin(Commands.class)
public class CommandsMixin {
    @Shadow
    @Final
    private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(at = @At("HEAD"), method = "performCommand", cancellable = true)
    public void performCommand(@Coerce Object parseResults, String string, CallbackInfo ci) {
        var event = new ServerCommandExecuteEvent(string);

        EventManager.fire(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"), method = "<init>")
    public void finishCommands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci) {
        new BrigadierSupport<CommandSourceStack>() {
            @Override
            protected <T extends ArgumentBuilder<CommandSourceStack, T>> T addNodePermissions(SyntaxTree entry, T node) {
                var perm = entry.getPermission();
                if (perm != null) {
                    if (perm instanceof pl.olafcio.avoid.net.command.annotation.Permission cast) {
                        var atom = Permission.Atom.create(cast.value());

                        node = node.requires(ctx -> {
                            if (!ctx.isPlayer())
                                return true;

                            var perms = ctx.getPlayer().permissions();
                            return perms.hasPermission(atom);
                        });
                    } else if (perm instanceof PermissionLevel cast) {
                        var atom = Permission.Atom.create(cast.value());
                        var cmdLevel = new Permission.HasCommandLevel(cast.level().__get());

                        node = node.requires(ctx -> {
                            if (!ctx.isPlayer())
                                return true;

                            var perms = ctx.getPlayer().permissions();
                            return perms.hasPermission(atom) || perms.hasPermission(cmdLevel);
                        });
                    } else {
                        throw new RuntimeException("Invalid command permission");
                    }
                }

                return node;
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
        }.finishCommands(dispatcher);
    }
}
