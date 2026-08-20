package pl.olafcio.avoid.net.effect;

import org.jetbrains.annotations.Range;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.world.World;

public abstract class Effect {
    /**
     * Returns whether should the effect apply this tick.
     * @param duration The remaining amount of ticks the effect should last.
     * @param amplifier The level of the effect.
     */
    public boolean shouldApply(
            int duration,
            @Range(from = 1, to = 256) int amplifier
    ) {
        return true;
    }

    /**
     * Tries to apply the effect.<br/>
     * If {@code false} is returned, the duration timer is not subtracted.
     * @param world The world that an entity is in that has the effect.
     * @param entity An entity that has the effect.
     * @param amplifier The level of the effect.
     */
    public abstract boolean tryApply(
            World world,
            Entity entity,
            @Range(from = 1, to = 256) int amplifier
    );

    /**
     * Invoked when the effect is added or updated on an entity.
     * @param entity The entity the effect has been added/updated on.
     * @param amplifier The level of the effect.
     */
    public void onAction(
            Entity entity,
            @Range(from = 1, to = 256) int amplifier
    ) {}
}
