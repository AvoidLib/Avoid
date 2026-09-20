package pl.olafcio.avoid.net.entity.values;

import java.util.HashMap;

public enum EnderDragonPhase {
    FLYING_ATTACKING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.STRAFE_PLAYER),
    FLYING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.HOLDING_PATTERN),
    GOING_TO_LAND(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.LANDING_APPROACH),
    LANDING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.LANDING),
    FINISHED_PERCHING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.TAKEOFF),
    CHARGING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.CHARGING_PLAYER),
    PERCHING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.SITTING_SCANNING),
    PERCHING_ATTACKING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.SITTING_ATTACKING),
    PERCHING_SHOOTING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.SITTING_FLAMING),
    HOVERING(net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.HOVERING);

    final Object value;

    EnderDragonPhase(Object mc) {
        this.value = mc;
    }

    static final HashMap<Object, EnderDragonPhase> REVERSE_MAP
           = new HashMap<>();

    static {
        var values = values();

        for (var val : values)
            REVERSE_MAP.put(val.value, val);
    }
}
