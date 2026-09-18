package pl.olafcio.avoid.net.entity.values;

import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@ApiStatus.Internal
@Native
public final class EnderDragonPhaseNative {
    @ApiStatus.Internal
    private EnderDragonPhaseNative() {}

    public static pl.olafcio.avoid.net.entity.values.EnderDragonPhase convert(EnderDragonPhase<?> phase) {
        return pl.olafcio.avoid.net.entity.values.EnderDragonPhase.REVERSE_MAP.get(phase);
    }

    public static EnderDragonPhase<?> convertFrom(pl.olafcio.avoid.net.entity.values.EnderDragonPhase phase) {
        return (EnderDragonPhase<?>) phase.value;
    }
}
