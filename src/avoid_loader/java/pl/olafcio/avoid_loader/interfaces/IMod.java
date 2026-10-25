package pl.olafcio.avoid_loader.interfaces;

import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;

import java.util.Collection;

/**
 * A mod object. Sometimes referred to as <i>mod container</i>.
 */
public interface IMod {
    IMod             parent();
    Collection<IMod> children();

    IModFile   getModFile();
    IModLoader getModLoader();

    /**
     * Returns a message to be used in the {@code Loading X addons:} Avoid addon listing.
     */
    String toListing();

    /**
     * Returns whether the addon should be listed in the Avoid addon listing.
     * @see #toListing
     */
    default boolean visible() {
        return true;
    }

    @Nullable
    default IModMetadata metadata() {
        return null;
    }
}
