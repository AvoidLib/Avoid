package pl.olafcio.avoid.net.item.component.internal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import pl.olafcio.avoid.net.item.component.values.SwingAnimationType;

public record Poly_SwingAnimation(SwingAnimationType type, int duration) {
    public static final Poly_SwingAnimation DEFAULT
                  = new Poly_SwingAnimation(SwingAnimationType.WHACK, 6);

    public static final Codec<Poly_SwingAnimation> CODEC
                      = RecordCodecBuilder.create(
                                instance -> instance.group(
                                        SwingAnimationType.CODEC.optionalFieldOf("type", DEFAULT.type).forGetter(Poly_SwingAnimation::type),
                                        ExtraCodecs.POSITIVE_INT.optionalFieldOf("duration", DEFAULT.duration).forGetter(Poly_SwingAnimation::duration)
                                )
                                .apply(instance, Poly_SwingAnimation::new)
                        );

    public static final StreamCodec<ByteBuf, Poly_SwingAnimation> STREAM_CODEC
                      = StreamCodec.composite(
                              SwingAnimationType.STREAM_CODEC, Poly_SwingAnimation::type,
                              ByteBufCodecs.VAR_INT, Poly_SwingAnimation::duration,
                              Poly_SwingAnimation::new
                        );

    public static final DataComponentType<Poly_SwingAnimation> SWING_ANIMATION =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Identifier.fromNamespaceAndPath("avoidlib", "swing_animation"),
                    DataComponentType.<Poly_SwingAnimation>builder()
                                     .persistent(Poly_SwingAnimation.CODEC)
                                     .networkSynchronized(Poly_SwingAnimation.STREAM_CODEC)
                                     .build()
            );

    public static void init() {}
}
