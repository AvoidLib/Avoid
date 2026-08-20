package pl.olafcio.avoid.net.item.component.internal;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record Poly_ZombieNautilusVariant(String id) {
    public static final Codec<Poly_ZombieNautilusVariant> CODEC
                      = Codec.STRING.xmap(Poly_ZombieNautilusVariant::new, Poly_ZombieNautilusVariant::id);

    public static final StreamCodec<ByteBuf, Poly_ZombieNautilusVariant> STREAM_CODEC
                      = ByteBufCodecs.STRING_UTF8.map(Poly_ZombieNautilusVariant::new, Poly_ZombieNautilusVariant::id);

    public static final DataComponentType<Poly_ZombieNautilusVariant> ZOMBIE_NAUTILUS_VARIANT =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Identifier.fromNamespaceAndPath("avoidlib", "zombie_nautilus_variant"),
                    DataComponentType.<Poly_ZombieNautilusVariant>builder()
                                     .persistent(Poly_ZombieNautilusVariant.CODEC)
                                     .networkSynchronized(Poly_ZombieNautilusVariant.STREAM_CODEC)
                                     .build()
            );

    public static void init() {}
}
