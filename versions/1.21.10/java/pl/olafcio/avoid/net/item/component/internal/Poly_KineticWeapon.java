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

public record Poly_KineticWeapon(
        int contactCooldownTicks,
        int delayTicks,
        Optional<Condition> dismountConditions,
        Optional<Condition> knockbackConditions,
        Optional<Condition> damageConditions,
        float forwardMovement,
        float damageMultiplier,
        Optional<Holder<SoundEvent>> sound,
        Optional<Holder<SoundEvent>> hitSound
) {
    public record Condition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
        public static final Codec<Poly_KineticWeapon.Condition> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("max_duration_ticks").forGetter(Poly_KineticWeapon.Condition::maxDurationTicks),
                                Codec.FLOAT.optionalFieldOf("min_speed", 0.0F).forGetter(Poly_KineticWeapon.Condition::minSpeed),
                                Codec.FLOAT.optionalFieldOf("min_relative_speed", 0.0F).forGetter(Poly_KineticWeapon.Condition::minRelativeSpeed)
                        )
                        .apply(instance, Poly_KineticWeapon.Condition::new)
        );
        public static final StreamCodec<ByteBuf, Poly_KineticWeapon.Condition> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT, Poly_KineticWeapon.Condition::maxDurationTicks,
                ByteBufCodecs.FLOAT, Poly_KineticWeapon.Condition::minSpeed,
                ByteBufCodecs.FLOAT, Poly_KineticWeapon.Condition::minRelativeSpeed,
                Poly_KineticWeapon.Condition::new
        );

        public boolean test(int i, double d, double e, double f) {
            return i <= this.maxDurationTicks && d >= this.minSpeed * f && e >= this.minRelativeSpeed * f;
        }

        public static Optional<Poly_KineticWeapon.Condition> ofAttackerSpeed(int i, float f) {
            return Optional.of(new Poly_KineticWeapon.Condition(i, f, 0.0F));
        }

        public static Optional<Poly_KineticWeapon.Condition> ofRelativeSpeed(int i, float f) {
            return Optional.of(new Poly_KineticWeapon.Condition(i, 0.0F, f));
        }
    }

    public static final Codec<Poly_KineticWeapon> CODEC
                      = RecordCodecBuilder.create(
                                instance -> instance.group(
                                        ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("contact_cooldown_ticks", 10).forGetter(Poly_KineticWeapon::contactCooldownTicks),
                                        ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("delay_ticks", 0).forGetter(Poly_KineticWeapon::delayTicks),
                                        Poly_KineticWeapon.Condition.CODEC.optionalFieldOf("dismount_conditions").forGetter(Poly_KineticWeapon::dismountConditions),
                                        Poly_KineticWeapon.Condition.CODEC.optionalFieldOf("knockback_conditions").forGetter(Poly_KineticWeapon::knockbackConditions),
                                        Poly_KineticWeapon.Condition.CODEC.optionalFieldOf("damage_conditions").forGetter(Poly_KineticWeapon::damageConditions),
                                        Codec.FLOAT.optionalFieldOf("forward_movement", 0.0F).forGetter(Poly_KineticWeapon::forwardMovement),
                                        Codec.FLOAT.optionalFieldOf("damage_multiplier", 1.0F).forGetter(Poly_KineticWeapon::damageMultiplier),
                                        SoundEvent.CODEC.optionalFieldOf("sound").forGetter(Poly_KineticWeapon::sound),
                                        SoundEvent.CODEC.optionalFieldOf("hit_sound").forGetter(Poly_KineticWeapon::hitSound)
                                )
                                .apply(instance, Poly_KineticWeapon::new)
                        );

    public static final StreamCodec<RegistryFriendlyByteBuf, Poly_KineticWeapon> STREAM_CODEC
                      = StreamCodec.composite(
                            ByteBufCodecs.VAR_INT, Poly_KineticWeapon::contactCooldownTicks,
                            ByteBufCodecs.VAR_INT, Poly_KineticWeapon::delayTicks,
                            Poly_KineticWeapon.Condition.STREAM_CODEC.apply(ByteBufCodecs::optional), Poly_KineticWeapon::dismountConditions,
                            Poly_KineticWeapon.Condition.STREAM_CODEC.apply(ByteBufCodecs::optional), Poly_KineticWeapon::knockbackConditions,
                            Poly_KineticWeapon.Condition.STREAM_CODEC.apply(ByteBufCodecs::optional), Poly_KineticWeapon::damageConditions,
                            ByteBufCodecs.FLOAT, Poly_KineticWeapon::forwardMovement,
                            ByteBufCodecs.FLOAT, Poly_KineticWeapon::damageMultiplier,
                            SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), Poly_KineticWeapon::sound,
                            SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional), Poly_KineticWeapon::hitSound,
                            Poly_KineticWeapon::new
                        );

    public static final DataComponentType<Poly_KineticWeapon> KINETIC_WEAPON =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Identifier.fromNamespaceAndPath("avoidlib", "kinetic_weapon"),
                    DataComponentType.<Poly_KineticWeapon>builder()
                                     .persistent(Poly_KineticWeapon.CODEC)
                                     .networkSynchronized(Poly_KineticWeapon.STREAM_CODEC)
                                     .build()
            );

    public static void init() {}
}
