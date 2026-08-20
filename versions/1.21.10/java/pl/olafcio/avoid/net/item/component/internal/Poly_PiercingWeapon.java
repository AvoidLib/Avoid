package pl.olafcio.avoid.net.item.component.internal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public record Poly_PiercingWeapon(
        boolean dealsKnockback,
        boolean dismounts,
        Optional<Holder<SoundEvent>> sound,
        Optional<Holder<SoundEvent>> hitSound
) {
    public static final Codec<Poly_PiercingWeapon> CODEC
                      = RecordCodecBuilder.create(
                                instance -> instance.group(
                                        Codec.BOOL.optionalFieldOf("deals_knockback", true).forGetter(Poly_PiercingWeapon::dealsKnockback),
                                        Codec.BOOL.optionalFieldOf("dismounts", false).forGetter(Poly_PiercingWeapon::dismounts),
                                        SoundEvent.CODEC.optionalFieldOf("sound").forGetter(Poly_PiercingWeapon::sound),
                                        SoundEvent.CODEC.optionalFieldOf("hit_sound").forGetter(Poly_PiercingWeapon::hitSound)
                                )
                                .apply(instance, Poly_PiercingWeapon::new)
                        );

    public static final StreamCodec<RegistryFriendlyByteBuf, Poly_PiercingWeapon> STREAM_CODEC
                      = StreamCodec.composite(
                                ByteBufCodecs.BOOL, Poly_PiercingWeapon::dealsKnockback,
                                ByteBufCodecs.BOOL, Poly_PiercingWeapon::dismounts,
                                SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), Poly_PiercingWeapon::sound,
                                SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), Poly_PiercingWeapon::hitSound,
                                Poly_PiercingWeapon::new
                        );

    public static final DataComponentType<Poly_PiercingWeapon> PIERCING_WEAPON =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Identifier.fromNamespaceAndPath("avoidlib", "piercing_weapon"),
                    DataComponentType.<Poly_PiercingWeapon>builder()
                                     .persistent(Poly_PiercingWeapon.CODEC)
                                     .networkSynchronized(Poly_PiercingWeapon.STREAM_CODEC)
                                     .build()
            );

    public static void init() {}
}
