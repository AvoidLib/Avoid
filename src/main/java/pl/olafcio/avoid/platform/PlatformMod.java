package pl.olafcio.avoid.platform;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

import java.util.List;
import java.util.Set;

@ApiStatus.Experimental
@Discouraged(reason = "This might be changed to be an interface or abstract class")
public record PlatformMod(
        String id,
        String name,
        String description,
        PlatformContributor[] authors,
        PlatformContributor[] contributors,
        PlatformContact contact,
        Set<String> licenses,
        String version,
        RunningEnv environment,
        List<PlatformDependency> dependencies,
        PlatformMod.Type type
) {
    public enum Type {
        BUILTIN,
        FABRIC,
        CUSTOM
    }
}
