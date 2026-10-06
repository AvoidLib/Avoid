package pl.olafcio.avoid_platform;

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
        throw new RuntimeException("[PreModLoader#addToClasspath] Not implemented on Sponge");
    }

    public static void addTransformer(ITransformer transformer) {
        throw new RuntimeException("[PreModLoader#addToClasspath] Not implemented on Sponge");
    }
}
