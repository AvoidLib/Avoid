package pl.olafcio.avoid_impl.net.datapack;

import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PathPackResources;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.net.datapack.pack.Datapack;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;

import java.nio.file.Path;
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
        AvoidInternal.getServer().getPackRepository().reload();
    }

    /**
     * Returns a stream of enabled datapacks on the running server.
     */
    @ServerOnly
    public static Stream<Datapack> getPacks() {
        return AvoidInternal.getServer().getPackRepository().getSelectedPacks().stream().map(pack -> new pl.olafcio.avoid_impl.net.datapack.pack.Datapack(
                pack.getId(),
                COFromNative.from(pack.getTitle()),
                COFromNative.from(pack.getDescription()),
                PackCompatibilityNative.convert(pack.getCompatibility()),
                getPath(pack)
        ));
    }

    /**
     * Returns a stream of all datapacks (both enabled and not enabled) on the running server.
     */
    @ServerOnly
    public static Stream<Datapack> getAllPacks() {
        return AvoidInternal.getServer().getPackRepository().getAvailablePacks().stream().map(pack -> new pl.olafcio.avoid_impl.net.datapack.pack.Datapack(
                pack.getId(),
                COFromNative.from(pack.getTitle()),
                COFromNative.from(pack.getDescription()),
                PackCompatibilityNative.convert(pack.getCompatibility()),
                getPath(pack)
        ));
    }

    private static Path getPath(net.minecraft.server.packs.repository.Pack pack) {
        if (pack.resources instanceof FilePackResources.FileResourcesSupplier fileSupplier)
            return fileSupplier.content.toPath();
        else if (pack.resources instanceof PathPackResources.PathResourcesSupplier pathSupplier)
            return pathSupplier.content;
        else
            return null;

        //at:getpathend
    }
}
