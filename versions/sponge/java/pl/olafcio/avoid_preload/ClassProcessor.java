package pl.olafcio.avoid_preload;

import cpw.mods.modlauncher.api.*;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipFile;

public class ClassProcessor implements ITransformationService {
    @Override
    public @NotNull String name() {
        return "avoidlib";
    }

    @Override
    public void initialize(IEnvironment environment) {
    }

    @Override
    public void onLoad(IEnvironment iEnvironment, Set<String> set) throws IncompatibleEnvironmentException {

    }

    @Override
    public @NotNull List<ITransformer> transformers() {
        return List.of(new ITransformer<ClassNode>() {
            @Override
            public @NotNull ClassNode transform(ClassNode classNode, ITransformerVotingContext context) {
                for (var transformer : PMLImpl.transformers) {
                    var name = classNode.name.replace("/", ".");
                    if (transformer.shouldTransform(name))
                        transformer.transform(name, classNode);
                }

                return classNode;
            }

            @Override
            public @NotNull TransformerVoteResult castVote(ITransformerVotingContext iTransformerVotingContext) {
                return TransformerVoteResult.NO;
            }

            @Override
            public @NotNull Set<Target> targets() {
                var minecraft_jar = Sponge.class.getClassLoader().getResource("META-INF/MANIFEST.MF").getPath().substring(8).split("!")[0];
                var targets = new HashSet<Target>();

                try (var zip = new ZipFile(minecraft_jar)) {
                    var entries = zip.entries();

                    while (entries.hasMoreElements()) {
                        var entry = entries.nextElement();

                        if (entry.getName().endsWith(".class")) {
                            targets.add(Target.targetClass(entry.getName().substring(0, entry.getName().length() - 6).replace("/", ".")));
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to iterate Minecraft classes for ClassProcessor", e);
                }

                return targets;
            }
        });
    }
}
