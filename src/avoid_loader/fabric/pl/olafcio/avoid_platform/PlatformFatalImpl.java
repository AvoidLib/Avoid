package pl.olafcio.avoid_platform;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.game.GameProvider;
import net.fabricmc.loader.impl.game.patch.GameTransformer;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.util.Arguments;
import org.slf4j.LoggerFactory;
import pl.olafcio.avoid_loader.PreModLoader;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.IModFile;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_loader.interfaces.services.stage.IPlatformFatal;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class PlatformFatalImpl implements IModLoader {
    @Override
    public void load() {
        if (!first) {
            var gameProvider = FabricLoaderImpl.INSTANCE.tryGetGameProvider();
            if (gameProvider == null) {
                // Well, what can i do..
                return;
            }

            LoggerFactory.getLogger("Avoid Loader/PlatformFatal")
                         .debug("Injected into Fabric's GameProvider");

            FabricLoaderImpl.INSTANCE.setGameProvider(new GameProvider() {
                @Override
                public String getGameId() {
                    return gameProvider.getGameId();
                }

                @Override
                public String getGameName() {
                    return gameProvider.getGameName();
                }

                @Override
                public String getRawGameVersion() {
                    return gameProvider.getRawGameVersion();
                }

                @Override
                public String getNormalizedGameVersion() {
                    return gameProvider.getNormalizedGameVersion();
                }

                @Override
                public Collection<BuiltinMod> getBuiltinMods() {
                    return gameProvider.getBuiltinMods();
                }

                @Override
                public String getEntrypoint() {
                    return gameProvider.getEntrypoint();
                }

                @Override
                public Path getLaunchDirectory() {
                    return gameProvider.getLaunchDirectory();
                }

                @Override
                public boolean requiresUrlClassLoader() {
                    return gameProvider.requiresUrlClassLoader();
                }

                @Override
                public Set<BuiltinTransform> getBuiltinTransforms(String className) {
                    return gameProvider.getBuiltinTransforms(className);
                }

                @Override
                public boolean isEnabled() {
                    return gameProvider.isEnabled();
                }

                @Override
                public boolean locateGame(FabricLauncher launcher, String[] args) {
                    return gameProvider.locateGame(launcher, args);
                }

                @Override
                public void initialize(FabricLauncher launcher) {
                    gameProvider.initialize(launcher);
                }

                @Override
                public GameTransformer getEntrypointTransformer() {
                    return gameProvider.getEntrypointTransformer();
                }

                @Override
                public void unlockClassPath(FabricLauncher launcher) {
                    gameProvider.unlockClassPath(launcher);
                }

                @Override
                public void launch(ClassLoader loader) {
                    gameProvider.launch(loader);
                }

                @Override
                public Arguments getArguments() {
                    return gameProvider.getArguments();
                }

                @Override
                public String[] getLaunchArguments(boolean sanitize) {
                    return gameProvider.getLaunchArguments(sanitize);
                }

                @Override
                public boolean displayCrash(Throwable exception, String context) {
                    var platformFatal = PreModLoader.getInstance().loadService(IPlatformFatal.class);
                    var event = new IPlatformFatal.CrashEvent() {
                        private boolean _value = !gameProvider.displayCrash(exception, context);

                        @Override
                        public boolean shouldDisplay() {
                            return _value;
                        }

                        @Override
                        public void shouldDisplay(boolean value) {
                            _value = value;
                        }
                    };

                    for (var service : platformFatal) {
                        service.acknowledge(event);
                    }

                    return !event.shouldDisplay();
                }

                @Override
                public String getRuntimeNamespace(String defaultNs) {
                    return gameProvider.getRuntimeNamespace(defaultNs);
                }

                @Override
                public String getDefaultModDistributionNamespace(String defaultNs) {
                    return gameProvider.getDefaultModDistributionNamespace(defaultNs);
                }

                @Override
                public boolean canOpenErrorGui() {
                    return gameProvider.canOpenErrorGui();
                }

                @Override
                public boolean hasAwtSupport() {
                    return gameProvider.hasAwtSupport();
                }
            });
        }
    }

    private boolean first = true;

    @Override
    public boolean needsAnotherRound(Stage stage) {
        if (first) {
            first = false;
            return true;
        }

        return false;
    }

    @Override
    public Collection<IMod> process(IModFile modFile) {
        return List.of();
    }
}
