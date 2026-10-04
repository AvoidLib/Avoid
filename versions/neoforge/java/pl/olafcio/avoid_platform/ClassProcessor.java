package pl.olafcio.avoid_platform;

import com.google.gson.Gson;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforgespi.transformation.ProcessorName;
import net.neoforged.neoforgespi.transformation.SimpleClassProcessor;
import net.neoforged.neoforgespi.transformation.SimpleTransformationContext;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipFile;

public class ClassProcessor extends SimpleClassProcessor {
    static {
        new Magician().onPreLaunch();
    }

    @Override
    public void transform(ClassNode classNode, SimpleTransformationContext simpleTransformationContext) {
        for (var transformer : PMLImpl.transformers) {
            var name = classNode.name.replace("/", ".");
            if (transformer.shouldTransform(name))
                transformer.transform(name, classNode);
        }
    }

    @Override
    public Set<Target> targets() {
        var minecraft_jar = FMLLoader.getCurrent().getGameLayer().findModule("minecraft").orElseThrow().getClassLoader().getResource("META-INF/MANIFEST.MF").getPath().substring(8).split("!")[0];
        var targets = new HashSet<Target>();

        try (var zip = new ZipFile(minecraft_jar)) {
            var entries = zip.entries();

            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();

                if (entry.getName().endsWith(".class")) {
                    targets.add(new Target(entry.getName().substring(0, entry.getName().length() - 6).replace("/", ".")));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to iterate Minecraft classes for ClassProcessor", e);
        }

        return targets;
    }

    @Override
    public ProcessorName name() {
        return new ProcessorName("avoidlib", "sub-tweaker");
    }
}