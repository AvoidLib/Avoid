package pl.olafcio.avoid_loader.interfaces.services;

import pl.olafcio.avoid_loader.interfaces.IModFile;

import java.util.List;

/**
 * Locates paths to try process as mods later.
 *
 * @see IModLoader
 * @service
 */
public interface IModFileLocator {
    /**
     * Returns paths of mods to try loading.
     */
    List<IModFile> getModFilePaths();
}
