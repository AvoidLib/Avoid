package pl.olafcio.avoid_impl.mixinclass;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.UnknownExecutor;

public final class MyUnknownExecutor extends UnknownExecutor {
    public final CommandSourceStack source;

    public MyUnknownExecutor(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public void sendMessage(BaseComponent<?> component) {
        source.sendSystemMessage(COToNative.from(component));
    }

    @Override
    public boolean hasPermission(String text) {
        return Permissions.check(source, text);
    }

    @Override
    public boolean hasPermission(String text, PermissionLevel.Enum level) {
        return Permissions.check(source, text, level.__get());
    }
}
