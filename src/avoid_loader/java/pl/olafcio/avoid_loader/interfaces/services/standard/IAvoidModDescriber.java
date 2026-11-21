package pl.olafcio.avoid_loader.interfaces.services.standard;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.standard.IAvoidModMetadata;
import pl.olafcio.avoid_platform.loader_impl.AvoidModLoader;

import java.io.IOException;

/**
 * Provides metadata for Avoid mods.
 * <p>
 * The first non-null metadata is chosen.
 *
 * @see AvoidModLoader
 * @service
 */
@ApiStatus.Experimental
public interface IAvoidModDescriber {
    @Nullable
    IAvoidModMetadata describe(ModFile modFile) throws IOException;
}
