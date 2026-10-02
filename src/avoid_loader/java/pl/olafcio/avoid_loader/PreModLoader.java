package pl.olafcio.avoid_loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid.AvoidWrappedLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PreModLoader {
    private PreModLoader() {}

    static final Logger LOGGER
               = LoggerFactory.getLogger("AvoidLoader");

    private static final ArrayList<PreModContainer> MODS
                   = new ArrayList<>();

    /**
     * Collects mods to load.
     */
    public static void preload() {
        try {
            var loadedMods = AvoidWrappedLoader.getModsPaths();

            loadFrom(AvoidWrappedLoader.getGameDir().resolve("mods"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("plugins"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidmods"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidplugins"), loadedMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidaddons"), loadedMods);

            LOGGER.info("Loading {} addons:{}", MODS.size(), MODS.stream()
                                                                 .map(mod -> mod.manifest().get("name").getAsString() + " " +
                                                                                            mod.manifest().get("version").getAsString())
                                                                 .map(text -> "\n - " + text)
                  .collect(Collectors.joining("")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to enumerate Avoid mods", e);
        }
    }

    public static List<PreModContainer> getMods() {
        return MODS;
    }

    private static void loadFrom(Path modsDir, Set<Path> loadedMods)
                   throws IOException
    {
        if (!Files.isDirectory(modsDir))
            return;

        try (var mods = Files.list(modsDir)) {
            mods.forEach(mod -> {
                if ((mod.toString().endsWith(".jar") || mod.toString().endsWith(".avoid.zip")) && !loadedMods.contains(mod)) {
                    new ModParser(mod, loadedMods, MODS).load();
                }
            });
        }
    }
}
