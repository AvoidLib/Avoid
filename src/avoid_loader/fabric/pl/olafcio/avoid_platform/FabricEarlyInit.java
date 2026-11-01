package pl.olafcio.avoid_platform;

import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.LanguageAdapterException;
import net.fabricmc.loader.api.ModContainer;
import pl.olafcio.avoid_loader.PreModLoader;

public final class FabricEarlyInit implements LanguageAdapter {
    static {
        PreModLoader.preload();
    }

    @Override
    public <T> T create(ModContainer mod, String value, Class<T> type) throws LanguageAdapterException {
        throw new UnsupportedOperationException("Cannot use the 'avoid' language adapter");
    }
}
