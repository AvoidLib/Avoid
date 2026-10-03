package pl.olafcio.avoid.mods.loader.subloader;

import org.objectweb.asm.tree.ClassNode;

/**
 * A class transformer.
 */
public interface ITransformer {
    /**
     * Returns whether {@link #transform} should be called for the {@code classname}.
     * @param className
     */
    boolean shouldTransform(String className);

    /**
     * Transforms {@code node} and returns whether it was transformed.
     * @param className
     * @param node
     * @return Whether any transformation actually took place.
     */
    boolean transform(String className, ClassNode node);
}
