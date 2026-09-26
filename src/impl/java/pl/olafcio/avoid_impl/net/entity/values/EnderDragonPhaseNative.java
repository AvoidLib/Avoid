package pl.olafcio.avoid_impl.net.entity.values;

import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@ApiStatus.Internal
@Native
public final class EnderDragonPhaseNative {
    @ApiStatus.Internal
    private EnderDragonPhaseNative() {}

    public static pl.olafcio.avoid.net.entity.values.EnderDragonPhase convert(EnderDragonPhase<?> phase) {
        if (phase == EnderDragonPhase.STRAFE_PLAYER)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.FLYING_ATTACKING;
        else if (phase == EnderDragonPhase.HOLDING_PATTERN)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.FLYING;
        else if (phase == EnderDragonPhase.LANDING_APPROACH)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.GOING_TO_LAND;
        else if (phase == EnderDragonPhase.LANDING)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.LANDING;
        else if (phase == EnderDragonPhase.TAKEOFF)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.FINISHED_PERCHING;
        else if (phase == EnderDragonPhase.CHARGING_PLAYER)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.CHARGING;
        else if (phase == EnderDragonPhase.SITTING_SCANNING)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.PERCHING;
        else if (phase == EnderDragonPhase.SITTING_ATTACKING)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.PERCHING_ATTACKING;
        else if (phase == EnderDragonPhase.SITTING_FLAMING)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.PERCHING_SHOOTING;
        else if (phase == EnderDragonPhase.HOVERING)
            return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.HOVERING;

        throw new UnsupportedOperationException("Unknown Minecraft ender-dragon-phase '%s'".formatted(phase));
    }

    public static EnderDragonPhase<?> convertFrom(pl.olafcio.avoid.net.entity.values.EnderDragonPhase phase) {
        if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.FLYING_ATTACKING)
            return EnderDragonPhase.STRAFE_PLAYER;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.FLYING)
            return EnderDragonPhase.HOLDING_PATTERN;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.GOING_TO_LAND)
            return EnderDragonPhase.LANDING_APPROACH;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.LANDING)
            return EnderDragonPhase.LANDING;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.FINISHED_PERCHING)
            return EnderDragonPhase.TAKEOFF;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.CHARGING)
            return EnderDragonPhase.CHARGING_PLAYER;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.PERCHING)
            return EnderDragonPhase.SITTING_SCANNING;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.PERCHING_ATTACKING)
            return EnderDragonPhase.SITTING_ATTACKING;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.PERCHING_SHOOTING)
            return EnderDragonPhase.SITTING_FLAMING;
        else if (phase == pl.olafcio.avoid.net.entity.values.EnderDragonPhase.HOVERING)
            return EnderDragonPhase.HOVERING;

        throw new UnsupportedOperationException("Unknown Avoid ender-dragon-phase '%s'".formatted(phase));
    }
}
