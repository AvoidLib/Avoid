package re.avoidmc.mcenvironment;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid_loader.PreModLoader;
import pl.olafcio.avoid_loader.subloaders.ITransformer;

import java.util.List;

public class MCEnvironmentAddon extends AvoidMod {
    @Override
    public void onEnable() {
        getLogger().info("Enabling AvoidMCEnvironment [builtin]");

        try {
            PreModLoader.addTransformer(new ITransformer() {

                private static final String MCEnvironment = MCEnvironment.class.getName().replace(".", "/");
                private static final String sideOn = AvoidWrappedLoader.getRunningEnvironment().name().toLowerCase();

                @Override
                public boolean shouldTransform(String className) {
                    return true;
                }

                @Override
                public boolean transform(String className, ClassNode node) {
                    if (isMismatch(node.invisibleAnnotations)) {
                        throw new UnsupportedOperationException("Tried to load class '%s' at %s-side".formatted(className, sideOn));
                    }

                    var b = new boolean[]{ false };

                    node.methods = node.methods.stream()
                                               .filter(m -> {
                                                   if (isMismatch(m.invisibleAnnotations)) {
                                                       b[0] = true;
                                                       return false;
                                                   } else {
                                                       return true;
                                                   }
                                               })
                                               .toList();

                    node.fields = node.fields.stream()
                                             .filter(m -> {
                                                 if (isMismatch(m.invisibleAnnotations)) {
                                                     b[0] = true;
                                                     return false;
                                                 } else {
                                                     return true;
                                                 }
                                             })
                                             .toList();

                    return b[0];
                }

                private boolean isMismatch(List<AnnotationNode> invisibleAnnotations) {
                    return invisibleAnnotations != null &&
                           invisibleAnnotations.stream().anyMatch(ann -> ann.desc.equals(MCEnvironment) &&
                                                                                       !ann.values.get(1).equals(sideOn));
                }

            });
        } catch (Exception e) {
            getLogger().warn("@MCEnvironment unsupported");
        }
    }
}
