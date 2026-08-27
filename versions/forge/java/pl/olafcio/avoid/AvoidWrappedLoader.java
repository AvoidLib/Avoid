package pl.olafcio.avoid;

import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    public static boolean isFabric() { return false; }
    public static boolean isNeoForge() { return false; }
    public static boolean isSponge() { return false; }

    public static Path getGameDir() {
        return FMLLoader.getCurrent().getGameDir();
    }

    public static boolean isModPresent(String id) {
        return FMLLoader.getCurrent().getLoadingModList().getModFileById(id) != null;
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
