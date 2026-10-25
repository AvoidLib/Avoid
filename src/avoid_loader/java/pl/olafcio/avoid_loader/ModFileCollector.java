package pl.olafcio.avoid_loader;

import pl.olafcio.avoid_loader.interfaces.services.IModFileLocator;
import pl.olafcio.avoid_loader.interfaces.IModFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ServiceLoader;

public final class ModFileCollector {
    private final HashSet<IModFile> modFiles
            = new HashSet<>();

    private final ArrayList<Class<? extends IModFileLocator>> loadedLoaders
            = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public HashSet<IModFile> collectModFiles()
    {
        // Load initially -- this will load the mods and prepare their locators
        load(ServiceLoader.load(IModFileLocator.class, this.getClass().getClassLoader()));

        // Load lastly -- this will load the mods' locators
        var clone = (HashSet<IModFile>) modFiles.clone();
        for (var file : clone)
            load(ServiceLoader.load(IModFileLocator.class, file.getClassLoader()));

        return modFiles;
    }

    private void load(ServiceLoader<IModFileLocator> serviceLoader)
    {
        for (var modFileLocator : serviceLoader) {
            var klass = modFileLocator.getClass();

            if (!(loadedLoaders.contains(klass))) {
                loadedLoaders.add(klass);
                modFiles.addAll(modFileLocator.getModFilePaths());
            }
        }
    }
}
