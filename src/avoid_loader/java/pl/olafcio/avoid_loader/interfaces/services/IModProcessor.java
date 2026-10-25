package pl.olafcio.avoid_loader.interfaces.services;

import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.round.IRoundable;

/**
 * Processes all mods after the mod listing appears.
 *
 * @see IModLoader
 * @see IModFileLocator
 * @see IModScanner
 * @service
 */
public interface IModProcessor extends IRoundable<IModProcessor.Stage> {
    enum Stage {
        LOAD,
        PROCESS
    }

    default void load() {}

    void process(IMod mod);
}
