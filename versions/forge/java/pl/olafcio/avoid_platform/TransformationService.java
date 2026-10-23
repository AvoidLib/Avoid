package pl.olafcio.avoid_service;

import cpw.mods.jarhandling.SecureJar;
import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.ModuleLayerHandler;
import cpw.mods.modlauncher.api.*;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import pl.olafcio.avoid_platform.Magician;
import pl.olafcio.avoid_platform.PMLImpl;
import pl.olafcio.avoid_platform.Reflect;
import pl.olafcio.avoid_service.ClassProcessor;

import java.util.*;

public class TransformationService implements ITransformationService {
    @Override
    public @NotNull String name() {
        return "avoid-sub-tweaker";
    }

    @Override
    public void initialize(IEnvironment iEnvironment) {

    }

    @Override
    public void onLoad(IEnvironment iEnvironment, Set<String> set) throws IncompatibleEnvironmentException {

    }

    @Override
    public @NotNull List<ITransformer> transformers() {
        return List.of(new ClassProcessor());
    }

    static {
        new Magician().onPreLaunch();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Resource> completeScan(IModuleLayerManager layerManager) {
        var out = new ArrayList<Resource>();

        for (var path : PMLImpl.classpath)
            out.add(new Resource(IModuleLayerManager.Layer.GAME, List.of(SecureJar.from(path))));

        PMLImpl.classpath = null;

//        var launchPlugins = Reflect.get(Launcher.class, "launchPlugins", LaunchPluginHandler.class, Launcher.INSTANCE);
//        var plugins = (Map<String, ILaunchPluginService>)
//                      Reflect.get(LaunchPluginHandler.class, "plugins", Map.class, launchPlugins);
//
//        plugins.put("avoid-post", new ILaunchPluginService() {
//            @Override
//            public String name() {
//                return "";
//            }
//
//            @Override
//            public boolean processClass(Phase phase, ClassNode classNode, Type classType) {
//                return false;
//            }
//
//            @Override
//            public void addResources(List<SecureJar> resources) {
//                for (var runnable : PMLImpl.postClasspath)
//                    runnable.run();
//
//                PMLImpl.postClasspath = null;
//            }
//
//            @Override
//            public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
//                return EnumSet.noneOf(Phase.class);
//            }
//        });

        return out;
    }

//    @Override
//    public List<Resource> beginScanning(IEnvironment environment) {
//    }
}
