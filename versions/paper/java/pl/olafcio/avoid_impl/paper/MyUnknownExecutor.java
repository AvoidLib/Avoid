package pl.olafcio.avoid_impl.paper;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.util.TriState;
import net.minecraft.server.permissions.Permission;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.executor.UnknownExecutor;
import pl.olafcio.avoid_impl.net.chat.converter_kyori.COToNative;

public final class MyUnknownExecutor extends UnknownExecutor {
    public final CommandSourceStack source;

    public MyUnknownExecutor(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public void sendMessage(BaseComponent<?> component) {
        source.getSender().sendMessage(COToNative.from(component));
    }

    @Override
    public boolean hasPermission(String text) {
        return source.getSender().hasPermission(text);
    }

    @Override
    public boolean hasPermission(String text, PermissionLevel.Enum level) {
        var perm = source.getSender().permissionValue(text);
        if (perm == TriState.NOT_SET)
            return source.getSender() instanceof Player player
                     ? Util.convert(player).permissions().hasPermission(new Permission.HasCommandLevel(level.__get()))
                     : source.getSender().isOp();

        //noinspection DataFlowIssue
        return perm.toBoolean();
    }
}
