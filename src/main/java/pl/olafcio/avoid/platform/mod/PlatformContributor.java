package pl.olafcio.avoid.platform.mod;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

/**
 * A mod/plugin contributor on the wrapped loader (the actual platform, e.g. Fabric/NeoForge/Paper).
 * @param name The nickname of the contributor.
 * @param contact The contributor's contact information.
 */
@ApiStatus.Experimental
@Discouraged(reason = "This might be changed to be an interface or abstract class")
public record PlatformContributor(String name, PlatformContact contact) {}
