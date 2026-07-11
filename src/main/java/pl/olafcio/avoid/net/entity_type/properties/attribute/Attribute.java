package pl.olafcio.avoid.net.entity_type.properties.attribute;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;
import java.util.Objects;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
@Documented
public @interface Attribute {
    Type type();
    double value();

    enum Type {
        ARMOR,
        ARMOR_TOUGHNESS,
        ATTACK_DAMAGE,
        ATTACK_KNOCKBACK,
        ATTACK_SPEED,
        BLOCK_BREAK_SPEED,
        BLOCK_INTERACTION_RANGE,
        BURNING_TIME,
        CAMERA_DISTANCE,
        EXPLOSION_KNOCKBACK_RESISTANCE,
        ENTITY_INTERACTION_RANGE,
        FALL_DAMAGE_MULTIPLIER,
        FLYING_SPEED,
        FOLLOW_RANGE,
        GRAVITY,
        JUMP_STRENGTH,
        KNOCKBACK_RESISTANCE,
        LUCK,
        MAX_ABSORPTION,
        MAX_HEALTH,
        MINING_EFFICIENCY,
        MOVEMENT_EFFICIENCY,
        MOVEMENT_SPEED,
        OXYGEN_BONUS,
        SAFE_FALL_DISTANCE,
        SCALE,
        SNEAKING_SPEED,
        SPAWN_REINFORCEMENTS_CHANCE("spawn_reinforcements"),
        STEP_HEIGHT,
        SUBMERGED_MINING_SPEED,
        SWEEPING_DAMAGE_RATIO,
        TEMPT_RANGE,
        WATER_MOVEMENT_EFFICIENCY,
        WAYPOINT_TRANSMIT_RANGE,
        WAYPOINT_RECEIVE_RANGE;

        private final String name;

        Type() {
            this.name = name().toLowerCase();
        }

        Type(String name) {
            this.name = name;
        }

        @ApiStatus.Internal
        public Holder<net.minecraft.world.entity.ai.attributes.Attribute> getMinecraft() {
            return Holder.direct(Objects.requireNonNull(BuiltInRegistries.ATTRIBUTE.getValue(Identifier.parse(name))));
        }
    }
}
