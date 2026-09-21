package pl.olafcio.avoid_impl;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.mods.events_loader.AllModsEnabledEvent;
import pl.olafcio.avoid.mods.events_loader.AllModsLoadedEvent;
import pl.olafcio.avoid.mods.events_loader.AllModsLoadingEvent;
import pl.olafcio.avoid.net.block.values.NoteBlockInstrument;
import pl.olafcio.avoid_common.LateInitializer;
import pl.olafcio.avoid_impl.mods.loader.ModLoad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class Avoid {
    @ApiStatus.Internal
    public static final Avoid INSTANCE
                  = new Avoid();

    private Avoid() {}

    public void onInitialize() {
        try {
            Class.forName("org.quiltmc.loader.api.QuiltLoader");

            throw new RuntimeException("Quilt is not supported and may break with AvoidLib. Please try Fabric instead; it runs 99% of the mods you use on Quilt.");
        } catch (ClassNotFoundException ignored) {
        }

        NoteBlockInstrument.clinit();
        EventManager.fire(new AllModsLoadedEvent());

        pl.olafcio.avoid.Avoid.INSTANCE.Realize();
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

            pl.olafcio.avoid.Avoid.LOGGER.info("Loaded {} addons:{}", avoidMods.size(), avoidMods.stream().map(text -> "\n - " + text)
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
