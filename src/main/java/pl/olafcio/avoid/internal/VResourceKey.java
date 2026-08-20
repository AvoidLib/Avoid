package pl.olafcio.avoid.internal;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public final class VResourceKey {
    private VResourceKey() {}

    public static Identifier identifier(ResourceKey<?> key) {
        return key.identifier();
    }
}
