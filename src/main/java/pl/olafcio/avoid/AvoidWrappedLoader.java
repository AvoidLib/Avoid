package pl.olafcio.avoid;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A wrapper over some of the parent running modloader's API.<br/><br/>
 * <b>NOTE:</b> If you want the Avoid addon loader API, take a look at {@link AvoidModLoader}.
 */
@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    /**
     * Returns the game directory.
     */
    public static Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    /**
     * Returns a list containing the JAR path of each loaded mod.
     */
    public static Set<Path> getModsPaths() {
        return FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getRootPaths)
                                                               .flatMap(Collection::stream)
                                                      .collect(Collectors.toSet());
    }

    /**
     * Returns the type of the running environment.
     */
    public static RunningEnv getRunningEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
                    ? RunningEnv.CLIENT
                    : RunningEnv.SERVER;
    }
}
