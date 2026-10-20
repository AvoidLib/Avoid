package pl.olafcio.avoid_impl.mixinclass;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.permissions.Permission;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.UnknownExecutor;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

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
        var id = IdentificationNative.convert(text);

        return source.permissions().hasPermission(
                !text.contains(":")
                        ? new UnassignedAtom(id)
                        : new Permission.Atom(id)
        );
    }

    @Override
    public boolean hasPermission(String text, PermissionLevel.Enum level) {
        var id = IdentificationNative.convert(text);

        return source.permissions().avoid$hasPermission(
                !text.contains(":")
                        ? new UnassignedAtom(id)
                        : new Permission.Atom(id),

                new Permission.HasCommandLevel(level.__get())
        );
    }
}
