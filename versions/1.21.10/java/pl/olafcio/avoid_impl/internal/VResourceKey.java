package pl.olafcio.avoid_impl.internal;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public final class VResourceKey {
    private VResourceKey() {}

    public static Identifier identifier(ResourceKey<?> key) {
        var loc = key.location();
        return Identifier.fromNamespaceAndPath(loc.getNamespace(), loc.getPath());
    }
}
