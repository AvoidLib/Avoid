package pl.olafcio.avoid_platform;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.game.GameProvider;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.launch.knot.Knot;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;
import org.spongepowered.asm.transformers.MixinClassReader;
import org.spongepowered.asm.transformers.MixinClassWriter;
import pl.olafcio.avoid_loader.subloaders.ITransformer;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.List;

@ApiStatus.Internal
public final class PMLImpl {
    private PMLImpl() {}

    public static void addToClasspath(Path path, String... startupPackages) {
        FabricLauncherBase.getLauncher().addToClassPath(path, startupPackages);
    }

    public static void addTransformer(ITransformer transformer) {
        try {
            var lookup = MethodHandles.privateLookupIn(Knot.class, MethodHandles.lookup());
            var KnotClassLoaderInterface = lookup.findClass("net.fabricmc.loader.impl.launch.knot.KnotClassLoaderInterface");

            var clface = Reflect.get(Knot.class, "classLoader", KnotClassLoaderInterface, (Knot) FabricLauncherBase.getLauncher());  // KnotClassDelegate
            var original = Reflect.get(clface.getClass(), "mixinTransformer", IMixinTransformer.class, clface);

            Reflect.set(clface.getClass(), "mixinTransformer", clface, new IMixinTransformer() {
                @Override
                public void audit(MixinEnvironment environment) {
                    original.audit(environment);
                }

                @Override
                public List<String> reload(String mixinClass, ClassNode classNode) {
                    return original.reload(mixinClass, classNode);
                }

                @Override
                public boolean computeFramesForClass(MixinEnvironment environment, String name, ClassNode classNode) {
                    return original.computeFramesForClass(environment, name, classNode);
                }

                private boolean skip(String name) {
                    var transforms = FabricLoaderImpl.INSTANCE.getGameProvider().getBuiltinTransforms(name);
                    return transforms.size() == 1 && transforms.contains(GameProvider.BuiltinTransform.STRIP_ENVIRONMENT);
                }

                @Override
                public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
                    if (basicClass != null && !skip(name) && transformer.shouldTransform(name)) {
                        var classReader = new MixinClassReader(basicClass, name);
                        var classNode = new ClassNode();

                        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

                        var transformed = transformer.transform(name, classNode);
                        if (transformed) {
                            var writer = new MixinClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                            classNode.accept(writer);
                            basicClass = writer.toByteArray();
                        }
                    }

                    return original.transformClassBytes(name, transformedName, basicClass);
                }

                @Override
                public byte[] transformClass(MixinEnvironment environment, String name, byte[] classBytes) {
                    if (!skip(name) && transformer.shouldTransform(name)) {
                        var classReader = new MixinClassReader(classBytes, name);
                        var classNode = new ClassNode();

                        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

                        var a = transformer.transform(name, classNode);
                        var b = original.transformClass(environment, name, classNode);

                        if (a || b) {
                            var writer = new MixinClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                            classNode.accept(writer);
                            return writer.toByteArray();
                        } else {
                            return classBytes;
                        }
                    }

                    return original.transformClass(environment, name, classBytes);
                }

                @Override
                public boolean transformClass(MixinEnvironment environment, String name, ClassNode classNode) {
                    if (!skip(name) && transformer.shouldTransform(name)) {
                        var a = transformer.transform(name, classNode);
                        var b = original.transformClass(environment, name, classNode);

                        return (a || b);
                    }

                    return original.transformClass(environment, name, classNode);
                }

                @Override
                public boolean couldTransformClass(MixinEnvironment environment, String name) {
                    var a = !skip(name) && transformer.shouldTransform(name);
                    var b = original.couldTransformClass(environment, name);

                    return (a || b);
                }

                @Override
                public byte[] generateClass(MixinEnvironment environment, String name) {
                    return original.generateClass(environment, name);
                }

                @Override
                public boolean generateClass(MixinEnvironment environment, String name, ClassNode classNode) {
                    return original.generateClass(environment, name, classNode);
                }

                @Override
                public IExtensionRegistry getExtensions() {
                    return original.getExtensions();
                }
            });
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to add transformer to PreModLoader  /ClassNotFoundException", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to add transformer to PreModLoader  /IllegalAccessException", e);
        }
    }
}
