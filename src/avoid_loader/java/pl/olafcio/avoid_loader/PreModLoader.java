package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.interfaces.*;
import pl.olafcio.avoid_loader.interfaces.round.IListRoundable;
import pl.olafcio.avoid_loader.interfaces.round.IRoundable;
import pl.olafcio.avoid_loader.interfaces.services.IModConflicter;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_loader.interfaces.services.IModProcessor;
import pl.olafcio.avoid_loader.interfaces.services.IModScanner;
import pl.olafcio.avoid_loader.subloaders.ITransformer;
import pl.olafcio.avoid_platform.PMLImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public final class PreModLoader {
    private final HashSet<IModFile> MOD_FILES;
    private static PreModLoader INSTANCE;

    private PreModLoader(HashSet<IModFile> modFiles) {
        MOD_FILES = modFiles;
        INSTANCE = this;
    }

    public static PreModLoader getInstance() {
        return INSTANCE;
    }

    static final Logger LOGGER
               = LoggerFactory.getLogger("AvoidLoader");

    private static final ArrayList<IMod> MODS
                   = new ArrayList<>();

    /**
     * Collects mods to load.
     */
    @SuppressWarnings("unchecked")
    public static void preload() {
        PreModLoader preModLoader;

        try {
            var modFiles = new ModFileCollector().collectModFiles();
            preModLoader = new PreModLoader(modFiles);

            // Loading -- calling IModLoader service
            var loaders = preModLoader.loadService(IModLoader.class);

            IRoundable.runEach(loaders, IModLoader.Stage.LOAD, IModLoader::load);
            IListRoundable.runEach(
                    loaders, IModLoader.Stage.PROCESS, modFiles,
                    (proc, modFile) -> {
                        MODS.addAll(proc.process(modFile));
                    },
                    els -> (HashSet<IModFile>) els.clone(),
                    set -> new HashSet<>(Arrays.stream(set).toList())
            );

            // Listing -- dumping the mod list
            LOGGER.info("Loading {} addons:{}", MODS.size(), MODS.stream()
                                                                 .filter(mod -> mod.parent() == null && mod.visible())
                                                                 .map(mod -> "\n   - " + PreModLoader.getModString(mod, 1))
                                                                 .collect(Collectors.joining("")));
        } catch (Throwable e) {
            throw new RuntimeException("Failed to enumerate Avoid mods", e);
        }

        // Scanning -- passing in mod containers along with JarFile-s open of them after they're loaded
        var scanners = preModLoader.loadService(IModScanner.class);

        IRoundable.runEach(scanners, IModScanner.Stage.LOAD, IModScanner::load);
        IRoundable.runEach(scanners, IModScanner.Stage.PROCESS, scanner -> {
            for (var mod : MODS) {
                try {
                    scanner.process(mod, mod.getModFile().getJar());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to scan Avoid mod with scanner: '%s' (%s)".formatted(scanner, scanner.getClass().getName()), e);
                }
            }
        });

        // Processing -- passing in mod containers after they're scanned
        var processors = preModLoader.loadService(IModProcessor.class);

        IRoundable.runEach(processors, IModProcessor.Stage.LOAD, IModProcessor::load);
        IRoundable.runEach(processors, IModProcessor.Stage.PROCESS, proc -> {
            for (var mod : MODS)
                proc.process(mod);
        });

        // Conflicting -- comparing each mod to each other to check if they're causing conflicts with each other
        var conflicters = preModLoader.loadService(IModConflicter.class);

        IRoundable.runEach(conflicters, IModConflicter.Stage.LOAD, IModConflicter::load);
        IRoundable.runEach(conflicters, IModConflicter.Stage.PROCESS, conflicter -> {
            master:
            while (true) {
                var clone = (List<IMod>) MODS.clone();

                for (var modA : clone) {
                    if (modA.metadata() == null)
                        continue;

                    for (var modB : clone) {
                        if (modA == modB || modB.metadata() == null)
                            continue;

                        var conflict = conflicter.check(modA, modB);
                        if (conflict.isEmpty())
                            continue;

                        if (conflict.get() == VersionSelector.VersionSide.B) {
                            MODS.remove(modA);
                            MODS.removeAll(modA.children());
                        } else {
                            MODS.remove(modB);
                            MODS.removeAll(modB.children());
                        }

                        continue master;
                    }
                }

                break;
            }
        });
    }

    private record ScanEntry(IMod mod, JarFile jar) {}

    @NotNull
    private static String getModString(IMod mod, int tab) {
        var text = new StringBuilder();

        text.append(mod.toListing());

        for (var child : mod.children()) {
            text.append("\n   ")
                .append(" ".repeat(tab))
                .append("\\_ ")
                .append(getModString(child, tab + 1));
        }

        return text.toString();
    }

    public static List<IMod> getMods() {
        return MODS;
    }

    /**
     * Adds a path to the game classpath.
     * @param path Usually a path to a JAR file.
     */
    public static void addToClasspath(Path path) {
        PMLImpl.addToClasspath(path);
    }

    /**
     * Adds a class transformer.
     */
    public static void addTransformer(ITransformer transformer) {
        PMLImpl.addTransformer(transformer);
    }

    public <T> Collection<T> loadService(Class<T> service) {
        var list = new HashMap<Class<?>, T>();

        for (var mod : MOD_FILES) {
            ServiceLoader.load(service, mod.getClassLoader())
                         .forEach(instance -> {
                             var klass = instance.getClass();
                             if (!list.containsKey(klass))
                                 list.put(klass, instance);
                         });
        }

        return list.values();
    }
}
