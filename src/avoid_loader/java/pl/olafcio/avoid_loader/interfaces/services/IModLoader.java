package pl.olafcio.avoid_loader.interfaces.services;

import pl.olafcio.avoid_loader.interfaces.round.IListRoundable;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.IModFile;

import java.util.Collection;
import java.util.HashSet;

/**
 * Converts mod files onto mod objects.
 *
 * @see IModFileLocator
 * @see IModProcessor
 * @service
 */
public interface IModLoader extends IListRoundable<IModLoader.Stage, IModFile, HashSet<IModFile>> {
    enum Stage {
        LOAD,
        PROCESS
    }

    default void load() {}

    Collection<IMod> process(IModFile modFile);
}
