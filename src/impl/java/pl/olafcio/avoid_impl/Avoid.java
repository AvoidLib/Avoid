package pl.olafcio.avoid_impl;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.mods.events_loader.AllModsEnabledEvent;
import pl.olafcio.avoid.mods.events_loader.AllModsLoadedEvent;
import pl.olafcio.avoid.mods.events_loader.AllModsLoadingEvent;
import pl.olafcio.avoid.platform.PlatformMod;
import pl.olafcio.avoid_impl.net.block.values.NoteBlockInstrumentNative;
import pl.olafcio.avoid_impl.mods.loader.ModLoad;
import pl.olafcio.avoid_loader.PreModContainer;
import pl.olafcio.avoid_loader.PreModLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
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

        NoteBlockInstrumentNative.clinit();
        EventManager.fire(new AllModsLoadedEvent());

        pl.olafcio.avoid.Avoid.INSTANCE.Realize();
        EventManager.fire(new AllModsEnabledEvent());
    }

    public void onEarlyInit() {
        EventManager.fire(new AllModsLoadingEvent());

        var loadedMods = new HashSet<String>();

        loadedMods.addAll(PreModLoader.getMods().stream().map(mod -> mod.manifest().get("id").getAsString()).toList());
        loadedMods.addAll(AvoidWrappedLoader.getMods().stream().map(PlatformMod::id).toList());

        PreModLoader.getMods().forEach(mod -> {
            load(mod, loadedMods);
        });
    }

    private void load(PreModContainer mod, Set<String> loadedMods) {
        new ModLoad(mod, loadedMods).load();
    }
}
