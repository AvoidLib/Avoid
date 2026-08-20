package pl.olafcio.avoid.net.block.values;

import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public record Explosion(
        World world,
        Entity directSourceEntity,
        Entity indirectSourceEntity,
        float radius,
        IVect3 center,
        boolean canTriggerBlocks,
        boolean shouldAffectBlocklikeEntities
) {}
