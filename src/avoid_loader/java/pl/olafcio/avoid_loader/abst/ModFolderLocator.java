package pl.olafcio.avoid_loader.abst;

import pl.olafcio.avoid_loader.interfaces.IModFile;
import pl.olafcio.avoid_loader.interfaces.services.IModFileLocator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class ModFolderLocator implements IModFileLocator {
    @Override
    public final List<IModFile> getModFilePaths() {
        var files = new ArrayList<IModFile>();
        var folders = getModFolderPaths();

        for (var folder : folders) {
            try {
                if (Files.isDirectory(folder)) {
                    try (var sub = Files.list(folder)) {
                        sub.forEachOrdered(file -> {
                            var modfile = new ModFile(file);
                            if (matches(modfile))
                                files.add(modfile);
                        });
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("[ModFolderLocator#getModFilePaths] Failed to list folder '%s' for %s".formatted(folder.toString(), this), e);
            }
        }

        return files;
    }

    public abstract List<Path> getModFolderPaths();

    protected boolean matches(ModFile modFile) {
        return true;
    }
}
