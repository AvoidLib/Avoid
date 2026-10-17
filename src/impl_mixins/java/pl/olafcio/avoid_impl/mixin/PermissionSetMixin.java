package pl.olafcio.avoid_impl.mixin;

import net.minecraft.server.permissions.PermissionSet;
import org.spongepowered.asm.mixin.Mixin;
import pl.olafcio.avoid_impl.mixininterface.IPermissionSet;

// I know I already do this in the accesswidener,
// but it won't work on NeoForge & Sponge.

@Mixin(PermissionSet.class)
public interface PermissionSetMixin extends IPermissionSet {
}
