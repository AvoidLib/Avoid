package pl.olafcio.avoid_platform.loader_impl;

import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.services.IModFileLocator;
import pl.olafcio.avoid_loader.abst.ModFolderLocator;

import java.nio.file.Path;
import java.util.List;

/**
 * A standard implementation for the {@link IModFileLocator}.
 */
public final class AvoidModFileLocator extends ModFolderLocator implements IModFileLocator {
    @Override
    public List<Path> getModFolderPaths() {
        return List.of(
                Path.of("mods"),
                Path.of("plugins"),
                Path.of("avoidmods"),
                Path.of("avoidplugins"),
                Path.of("avoidaddons")
        );
    }

    @Override
    protected boolean matches(ModFile modFile) {
        var mod = modFile.getPath();

        return mod.toString().endsWith(".jar") ||
               mod.toString().endsWith(".avoid.zip");
    }
}
