package pl.olafcio.avoid.mixinclass;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

public final class EntityUtil {
    private EntityUtil() {}

    public static boolean isLocal(Entity entity) {
        return entity instanceof LocalPlayer;
    }
}
