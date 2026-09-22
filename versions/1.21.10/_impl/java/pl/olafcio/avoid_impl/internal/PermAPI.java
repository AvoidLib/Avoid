package pl.olafcio.avoid.internal;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;

public final class PermAPI {
    private PermAPI() {}

    public static boolean check(Player player, String perm) {
        return Permissions.check(player, perm);
    }

    public static boolean check(Player player, String perm, int level) {
        return Permissions.check(player, perm, level);
    }
}
