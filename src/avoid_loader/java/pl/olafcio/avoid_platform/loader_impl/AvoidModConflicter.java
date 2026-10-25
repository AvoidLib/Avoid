package pl.olafcio.avoid_platform.loader_impl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.VersionSelector;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.services.IModConflicter;

import java.util.Objects;
import java.util.Optional;

public final class AvoidModConflicter implements IModConflicter {
    private static final Logger LOGGER
                       = LoggerFactory.getLogger("AvoidLoader/Mod Conflicter");

    private final VersionSelector versionSelector
            = new VersionSelector();

    @Override
    @NotNull
    public Optional<VersionSelector.VersionSide> check(IMod imodA, IMod imodB) {
        var modA = imodA.metadata();
        var modB = imodB.metadata();

        assert modA != null;
        assert modB != null;

        if (Objects.equals(modA.id(), modB.id())) {
            var pick = versionSelector.pickVersion(
                    modA.version(),
                    modB.version()
            );

            LOGGER.error("You have 2 addons using the same ID: '{}' ({}) and '{}' ({})\nPicking {} ({})",
                    imodA.getModFile().getPath().toAbsolutePath(), modA.id(),
                    imodB.getModFile().getPath().toAbsolutePath(), modB.id(),
                    pick.name(),
                    (pick == VersionSelector.VersionSide.B ? modB : modA).id()
            );

            return Optional.of(pick);
        }

        return Optional.empty();
    }
}
