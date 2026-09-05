package pl.olafcio.avoid.net.fog;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.fog.delta.TickTracker;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.World;

/**
 * A fog is a transparency effect.
 * <br/><br/><hr/><br/>
 * <b>Fogs can happen in very many situations:</b>
 * <ul>
 *     <li>with an effect,&emsp;&ensp; <i>(Nausea, Darkness)</i></li>
 *     <li>inside a fluid,&emsp;&ensp;&nbsp;&nbsp; <i>(Lava, Water)</i></li>
 *     <li>inside a block,&emsp;&ensp; <i>(Powdered Snow)</i></li>
 *     <li>depending on where the sun is.</li>
 * </ul>
 * <br/><hr/><br/>
 * <b>As a mod scenario of where you could add a fog - well, here's a few:</b>
 * <br/>
 * <ul>
 *     <li>in  a horror scene,</li>
 *     <li>for different biome sky colors,</li>
 *     <li>for more scary caves.</li>
 * </ul>
 */
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
