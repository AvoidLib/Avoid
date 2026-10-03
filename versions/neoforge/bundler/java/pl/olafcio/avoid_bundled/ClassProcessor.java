package pl.olafcio.avoid_bundled;

import com.google.gson.Gson;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforgespi.transformation.ProcessorName;
import net.neoforged.neoforgespi.transformation.SimpleClassProcessor;
import net.neoforged.neoforgespi.transformation.SimpleTransformationContext;
import org.objectweb.asm.tree.ClassNode;

import java.util.Set;

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
        IO.println("Doing shit!");

        return Set.of();
    }

    @Override
    public ProcessorName name() {
        return new ProcessorName("avoidlib", "SubTweaker");
    }
}
