package pl.olafcio.avoid.net.datapack;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.datapack.pack.Datapack;

import java.util.stream.Stream;

/**
 * A namespace for managing datapacks on the running server.
 */
@ApiStatus.Experimental
public final class DatapackManager {
    @ApiStatus.Internal
    private DatapackManager() {}

    /**
     * Reloads the running server's datapack repository.
     */
    @ServerOnly
    public static void refresh() {
        pl.olafcio.avoid_impl.net.datapack.DatapackManager.refresh();
    }

    /**
     * Returns a stream of enabled datapacks on the running server.
     */
    @ServerOnly
    public static Stream<Datapack> getPacks() {
        return pl.olafcio.avoid_impl.net.datapack.DatapackManager.getPacks();
    }

    /**
     * Returns a stream of all datapacks (both enabled and not enabled) on the running server.
     */
    @ServerOnly
    public static Stream<Datapack> getAllPacks() {
        return pl.olafcio.avoid_impl.net.datapack.DatapackManager.getAllPacks();
    }
}
