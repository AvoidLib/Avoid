package pl.olafcio.avoid_loader.interfaces.services;

import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.round.IRoundable;

import java.io.IOException;
import java.util.jar.JarFile;

/**
 * Scans something in each mod's JAR file.
 *
 * @see IModProcessor
 * @service
 */
public interface IModScanner extends IRoundable<IModScanner.Stage> {
    enum Stage {
        LOAD,
        PROCESS
    }

    default void load() {}

    void process(IMod mod, JarFile jar) throws IOException;
}
