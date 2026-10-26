package pl.olafcio.avoid_loader.interfaces.services;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid_loader.VersionSelector;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.round.IRoundable;

import java.util.Optional;

/**
 * Detects conflicts between mods.
 *
 * @see IModProcessor
 * @service
 */
public interface IModConflicter extends IRoundable<IModConflicter.Stage> {
    enum Stage {
        LOAD,
        PROCESS
    }

    default void load() {}

    /**
     * If a conflict is detected, this should log a warning/error/fatal message, and optionally either:
     * <ul>
     *     <li>return one of the conflicting mods<p>&emsp;<i>({@linkplain VersionSelector.VersionSide#A} if modA, {@linkplain VersionSelector.VersionSide#B} if modB)</i><p>in order to cause the other one to be unloaded,</li>
     *     <li>initiate a game crash.</li>
     * </ul>
     * <p><p>
     * Each of the provided mods have a non-null {@link IMod#metadata()}.
     */
    @NotNull
    Optional<VersionSelector.VersionSide> check(IMod modA, IMod modB);
}
