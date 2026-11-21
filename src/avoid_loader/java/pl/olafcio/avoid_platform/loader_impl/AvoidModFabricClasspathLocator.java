package pl.olafcio.avoid_platform.loader_impl;

import pl.olafcio.avoid_loader.abst.ModFile;
import pl.olafcio.avoid_loader.interfaces.IModFile;
import pl.olafcio.avoid_loader.interfaces.services.IModFileLocator;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@code -classpath} targeting implementation for the {@link IModFileLocator}.
 */
public final class AvoidModFabricClasspathLocator implements IModFileLocator {
    @Override
    public List<IModFile> getModFilePaths() {
        var prop = System.getProperty("fabric.classPathGroups");
        if (prop == null)
            return List.of();

        var output = new ArrayList<IModFile>();

        for (var paths : prop.split(File.pathSeparator + File.pathSeparator))
            output.add(ModFile.forDirectories(Arrays.stream(paths.split(File.pathSeparator)).map(Path::of).toArray(Path[]::new)));

        return output;
    }
}
