package pl.olafcio.avoid_impl.mixin;

import net.minecraft.server.players.NameAndId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import pl.olafcio.avoid.net.permission.PermissionHolder;
import pl.olafcio.avoid_impl.mixininterface.INameAndId;

@Mixin(NameAndId.class)
public class NameAndIdMixin implements INameAndId {
    @Unique
    private PermissionHolder owner;

    @Override
    public PermissionHolder avoid$owner() {
        return owner;
    }

    public void avoid$owner(PermissionHolder value) {
        owner = value;
    }
}
