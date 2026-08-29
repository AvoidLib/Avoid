package pl.olafcio.avoid;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.mods.events_loader.AllModsEnabledEvent;
import pl.olafcio.avoid.mods.events_loader.AllModsLoadedEvent;
import pl.olafcio.avoid.mods.events_loader.AllModsLoadingEvent;
import pl.olafcio.avoid.mods.loader.ModLoad;
import pl.olafcio.avoid.net.block.values.NoteBlockInstrument;
import pl.olafcio.avoid_lateinit.LateInitializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class Avoid extends LateInitializer {
    @ApiStatus.Internal
    public static final Logger LOGGER
                      = LogUtils.getLogger();

    @ApiStatus.Internal
    public static final Avoid INSTANCE
                  = new Avoid();

    private static final String VERSION;

    public static String getVersion() {
        return VERSION;
    }

    static {
        try (var stream = Avoid.class.getResourceAsStream("/.version")) {
            VERSION = new String(stream.readAllBytes(), StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            throw new RuntimeException("AvoidLib failed to get its version", e);
        }
    }

    private Avoid() {}

    public void onInitialize() {
        NoteBlockInstrument.clinit();
        EventManager.fire(new AllModsLoadedEvent());

        Realize();
        EventManager.fire(new AllModsEnabledEvent());
    }

    public void onEarlyInit() {
        EventManager.fire(new AllModsLoadingEvent());

        try {
            var loadedMods = AvoidWrappedLoader.getModsPaths();
            var avoidMods = new ArrayList<String>();

            loadFrom(AvoidWrappedLoader.getGameDir().resolve("mods"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("plugins"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidmods"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidplugins"), loadedMods, avoidMods);
            loadFrom(AvoidWrappedLoader.getGameDir().resolve("avoidaddons"), loadedMods, avoidMods);

            LOGGER.info("Loaded {} addons:{}", avoidMods.size(), avoidMods.stream().map(text -> "\n - " + text)
                                                                          .collect(Collectors.joining("")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to enumerate Avoid mods", e);
        }
    }

    private void loadFrom(Path modsDir, Set<Path> loadedMods, ArrayList<String> avoidMods)
            throws IOException
    {
        if (!Files.isDirectory(modsDir))
            return;

        try (var mods = Files.list(modsDir)) {
            mods.forEach(mod -> {
                if ((mod.toString().endsWith(".jar") || mod.toString().endsWith(".avoid.zip")) && !loadedMods.contains(mod)) {
                    new ModLoad(mod, loadedMods, avoidMods).load();
                }
            });
        }
    }
}
