package pl.olafcio.avoid_impl.mixinclass;

import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.server.permissions.PermissionSet;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.permission.PermissionHolder;
import pl.olafcio.avoid.net.permission.PermissionStatus;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid_impl.net.permission.IUnassignedAtom;
import pl.olafcio.avoid_impl.net.permission.PermissionHandlers;

import java.util.ArrayList;
import java.util.Comparator;

public class AvoidBasePermissionSet<T extends PermissionSet> implements PermissionSet {
    protected final T delegate;
    protected final PermissionHolder holder;

    public AvoidBasePermissionSet(T delegate, PermissionHolder holder) {
        this.delegate = delegate;
        this.holder = holder;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        var vote = castVote(permission);
        if (vote != null)
            return vote;

        return delegate.hasPermission(permission);
    }

    @Nullable
    private Boolean castVote(Permission permission) {
        if (permission instanceof Permission.HasCommandLevel(PermissionLevel level)) {
            var handlers = PermissionHandlers.PERMISSION_HANDLERS;
            var votes = new ArrayList<PermissionStatus>();

            for (var handler : handlers) {
                var vote = handler.hasPermissionLevel(
                        holder,
                        pl.olafcio.avoid.net.command.annotation.PermissionLevel.Enum.reverse.get(level)
                );

                if (vote.has() != null)
                    votes.add(vote);
            }

            votes.sort(Comparator.comparingInt(PermissionStatus::priority));

            if (!votes.isEmpty())
                return votes.getFirst().has();
        } else if (permission instanceof Permission.Atom atom) {
            var handlers = PermissionHandlers.PERMISSION_HANDLERS;
            var votes = new ArrayList<PermissionStatus>();

            for (var handler : handlers) {
                var id = atom.id();
                var vote = handler.hasPermission(
                        holder,
                        atom instanceof IUnassignedAtom ? id.getPath() : IdentificationNative.convertFrom(id).toString()
                );

                if (vote.has() != null)
                    votes.add(vote);
            }

            votes.sort(Comparator.comparingInt(PermissionStatus::priority));

            if (!votes.isEmpty())
                return votes.getFirst().has();
        }

        return null;
    }

    @Override
    public boolean avoid$hasPermission(Permission.Atom atom, Permission.HasCommandLevel level) {
        var vote = castVote(atom);
        if (vote != null)
            return vote;

        var vote2 = castVote(level);
        if (vote2 != null)
            return vote2;

        return delegate.avoid$hasPermission(atom, level);
    }

    @Override
    public PermissionSet union(PermissionSet permissionSet) {
        return delegate.union(permissionSet);
    }
}
