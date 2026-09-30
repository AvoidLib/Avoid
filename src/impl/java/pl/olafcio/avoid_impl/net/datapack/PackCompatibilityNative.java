package pl.olafcio.avoid_impl.net.datapack;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.datapack.pack.DatapackCompatibility;

@Native
@ApiStatus.Internal
public final class PackCompatibilityNative {
    @ApiStatus.Internal
    private PackCompatibilityNative() {}

    public static DatapackCompatibility convert(net.minecraft.server.packs.repository.PackCompatibility compatibility) {
        return switch (compatibility) {
            case TOO_OLD    -> DatapackCompatibility.TOO_OLD;
            case TOO_NEW    -> DatapackCompatibility.TOO_NEW;
            case UNKNOWN    -> DatapackCompatibility.UNKNOWN;
            case COMPATIBLE -> DatapackCompatibility.COMPATIBLE;
        };
    }
}
