package pl.olafcio.avoid;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@ApiStatus.Experimental
public final class AvoidWrappedLoader {
    @ApiStatus.Internal
    private AvoidWrappedLoader() {}

    public static boolean isFabric() { return false; }
    public static boolean isNeoForge() { return false; }
    public static boolean isSponge() { return false; }
    public static boolean isPaper() { return true; }

    public static Path getGameDir() {
        return Bukkit.getPluginsFolder().toPath().getParent();
    }

    public static boolean isModPresent(String id) {
        return Bukkit.getPluginManager().isPluginEnabled(id);
    }

    public static Set<Path> getModsPaths() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins())
                     .map(plug -> plug.getClass().getResource("/"))
                     .map(res -> Arrays.stream(res.toString().split(":")).toList())
                     .map(list -> {
                         return list.get(list.size() - 2) + list.getLast().split("!/")[0];
                     })
                     .map(path -> {
                         while (path.startsWith("/"))
                             path = path.substring(1);

                         return path;
                     })
                     .map(Path::of)
                     .collect(Collectors.toSet());
    }

    public static RunningEnv getRunningEnvironment() {
        return RunningEnv.SERVER;
    }
}
