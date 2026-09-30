package pl.olafcio.avoid_impl;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.mods.ModEnvironment;
import pl.olafcio.avoid.platform.PlatformContact;
import pl.olafcio.avoid.platform.PlatformContributor;
import pl.olafcio.avoid.platform.PlatformDependency;
import pl.olafcio.avoid.platform.PlatformMod;

import java.nio.file.Path;
import java.util.*;
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

    public static List<PlatformMod> getMods() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(mod -> {
            var meta = mod.getPluginMeta();
            var dependencies = new ArrayList<PlatformDependency>();

            for (var id : meta.getPluginDependencies())
                dependencies.add(new PlatformDependency(
                        id,
                        PlatformDependency.Hard.REQUIRES,
                        PlatformDependency.LoadOrder.BEFORE_MOD
                ));

            for (var id : meta.getPluginSoftDependencies())
                dependencies.add(new PlatformDependency(
                        id,
                        PlatformDependency.Hard.RECOMMENDS,
                        PlatformDependency.LoadOrder.BEFORE_MOD
                ));

            for (var id : meta.getLoadBeforePlugins())
                dependencies.add(new PlatformDependency(
                        id,
                        PlatformDependency.Soft.RECOMMENDS,
                        PlatformDependency.LoadOrder.BEFORE_MOD
                ));

            for (var id : meta.getProvidedPlugins())
                dependencies.add(new PlatformDependency(
                        id,
                        PlatformDependency.Soft.PROVIDES,
                        PlatformDependency.LoadOrder.BEFORE_MOD
                ));

            return new PlatformMod(
                    meta.getName(),
                    meta.getDisplayName(),
                    meta.getDescription(),
                    meta.getAuthors().stream()
                                     .map(person -> new PlatformContributor(
                                                                    person,
                                                                    new PlatformContact(Map.of())
                                                           )
                                     )
                                     .toArray(PlatformContributor[]::new),
                    meta.getContributors().stream()
                                          .map(person -> new PlatformContributor(
                                                                    person,
                                                                    new PlatformContact(Map.of())
                                                                )
                                          )
                                          .toArray(PlatformContributor[]::new),
                    new PlatformContact(new HashMap<>() {{
                        if (meta.getWebsite() != null)
                            put("homepage", meta.getWebsite());
                    }}),
                    Set.of(),
                    meta.getVersion(),
                    ModEnvironment.SERVER,
                    dependencies,
                    PlatformMod.Type.PAPER
            );
        }).collect(Collectors.toList());
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
