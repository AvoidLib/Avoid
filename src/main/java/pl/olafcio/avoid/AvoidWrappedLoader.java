package pl.olafcio.avoid;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    public static Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    public static Set<Path> getModsPaths() {
        return FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getRootPaths)
                                                               .flatMap(Collection::stream)
                                                      .collect(Collectors.toSet());
    }

    public static RunningEnv getRunningEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
                    ? RunningEnv.CLIENT
                    : RunningEnv.SERVER;
    }
}
