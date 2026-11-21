package pl.olafcio.avoid_platform;

import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.IModFile;

import java.util.Collection;
import java.util.List;

public final class PlatformFatalImpl implements IModLoader {
    @Override
    public Collection<IMod> process(IModFile modFile) {
        return List.of();
    }
}
