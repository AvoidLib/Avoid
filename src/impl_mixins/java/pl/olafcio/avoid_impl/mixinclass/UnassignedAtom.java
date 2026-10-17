package pl.olafcio.avoid_impl.mixinclass;

import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permission;
import pl.olafcio.avoid_impl.net.permission.IUnassignedAtom;

public final class UnassignedAtom
        extends Permission.Atom
        implements IUnassignedAtom
{
    public UnassignedAtom(Identifier id) {
        super(id);
    }
}
