package pl.olafcio.avoid_service;

import net.minecraftforge.fml.loading.FMLLoader;
import cpw.mods.modlauncher.api.*;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import pl.olafcio.avoid_platform.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipFile;

public class ClassProcessor implements ITransformer<ClassNode> {
    @Override
    @NotNull
    public ClassNode transform(ClassNode classNode, ITransformerVotingContext simpleTransformationContext) {
        for (var transformer : PMLImpl.transformers) {
            var name = classNode.name.replace("/", ".");
            if (transformer.shouldTransform(name))
                transformer.transform(name, classNode);
        }

        return classNode;
    }

    @Override
    @NotNull
    public TransformerVoteResult castVote(ITransformerVotingContext iTransformerVotingContext) {
        return TransformerVoteResult.YES;
    }

    @Override
    public Set<Target> targets() {
        var minecraft_jar = FMLLoader.getLoadingModList().getModFileById("minecraft").getFile().getFilePath().toFile();
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
}
