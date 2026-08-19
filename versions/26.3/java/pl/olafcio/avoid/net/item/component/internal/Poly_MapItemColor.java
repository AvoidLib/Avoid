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

public record Poly_MapItemColor(int rgb) {
    public static final Codec<Poly_MapItemColor> CODEC
                      = Codec.INT.xmap(Poly_MapItemColor::new, Poly_MapItemColor::rgb);

    public static final StreamCodec<ByteBuf, Poly_MapItemColor> STREAM_CODEC
                      = ByteBufCodecs.INT.map(Poly_MapItemColor::new, Poly_MapItemColor::rgb);

    public static final Poly_MapItemColor DEFAULT
                  = new Poly_MapItemColor(0x46402e);

    public static final DataComponentType<Poly_MapItemColor> MAP_COLOR =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Identifier.fromNamespaceAndPath("avoidlib", "map_color"),
                    DataComponentType.<Poly_MapItemColor>builder()
                                     .persistent(Poly_MapItemColor.CODEC)
                                     .networkSynchronized(Poly_MapItemColor.STREAM_CODEC)
                                     .build()
            );

    public static void init() {}
}
