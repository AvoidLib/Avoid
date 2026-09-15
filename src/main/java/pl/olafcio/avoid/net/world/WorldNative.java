package pl.olafcio.avoid.net.world;

import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.ArrayList;

@Native
@ApiStatus.Internal
public final class WorldNative {
    @ApiStatus.Internal
    private WorldNative() {}

    public static final ArrayList<BlockOutlineRenderState> highlights
                  = new ArrayList<>();

    public static World make(Level level) {
        return new World(level);
    }

    public static Level convert(World level) {
        return level.level;
    }
}
