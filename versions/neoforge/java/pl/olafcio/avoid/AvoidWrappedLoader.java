package pl.olafcio.avoid;

import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    public static boolean isFabric() { return false; }
    public static boolean isNeoForge() { return true; }

    public static Path getGameDir() {
        return FMLLoader.getCurrent().getGameDir();
    }

    public static Set<Path> getModsPaths() {
        return FMLLoader.getCurrent().getLoadingModList().getModFiles()
                                                         .stream().map(ModFileInfo::getFile)
                                                                  .map(ModFile::getFilePath)
                                                         .collect(Collectors.toSet());
    }

    public static RunningEnv getRunningEnvironment() {
        return FMLEnvironment.getDist().isClient()
                    ? RunningEnv.CLIENT
                    : RunningEnv.SERVER;
    }
}
