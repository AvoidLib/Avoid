package pl.olafcio.avoid.net.fog;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.fog.delta.TickTracker;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.World;

@NullMarked
public abstract class Fog {
    public int baseColor(World world, Entity entity, int renderDistance, float deltaTicks) {
        return -1;
    }

    /**
     * Should setup the fog.
     * @param fog The fog state that this method has to write to.
     * @param entity The camera entity.
     * @param world The camera world.
     * @param blockDistance The render distance multiplied by 16.
     * @param tickTracker
     */
    public abstract void createFog(FogState fog, Entity entity, World world, float blockDistance, TickTracker tickTracker);

    /**
     * Returns whether the fog should be shown.
     * @param fogType A Minecraft fog type or any fluid ID.
     * @param entity The camera entity.
     */
    public abstract boolean shouldApply(@Nullable Identification fogType, Entity entity);
}
