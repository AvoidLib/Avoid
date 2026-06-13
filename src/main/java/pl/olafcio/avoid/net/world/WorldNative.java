package pl.olafcio.avoid.net.world;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class WorldNative {
    @ApiStatus.Internal
    private WorldNative() {}

    public static World make(Level level) {
        return new World(level);
    }
}
