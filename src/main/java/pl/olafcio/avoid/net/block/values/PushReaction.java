package pl.olafcio.avoid.net.block.values;

/**
 * The behaviour a block or entity reacts with when requested motion by a piston.
 */
public enum PushReaction {
    /**
     * Indicates the block/entity can be moved by pistons and sticky pistons in both ways.
     */
    NORMAL(net.minecraft.world.level.material.PushReaction.NORMAL),

    /**
     * Indicates the block gets destroyed when pushed by a piston.
     */
    DESTROY(net.minecraft.world.level.material.PushReaction.DESTROY),

    /**
     * Indicates the block cannot be managed by pistons at all.<br/><br/>
     * <b>NOTE:</b> This is a block-only push reaction.<br/>
     * &emsp;&ensp;&ensp;&ensp;&ensp;&nbsp;For entities, use {@link #IGNORE PushReaction.IGNORE} instead.
     */
    BLOCK(net.minecraft.world.level.material.PushReaction.BLOCK),

    /**
     * Indicates the entity cannot be managed by pistons at all.<br/><br/>
     * <b>NOTE:</b> This is a entity-only push reaction.<br/>
     * &emsp;&ensp;&ensp;&ensp;&ensp;&nbsp;For blocks, use {@link #BLOCK PushReaction.BLOCK} instead.
     */
    IGNORE(net.minecraft.world.level.material.PushReaction.IGNORE),

    /**
     * Indicates the block/entity can get pushed by a piston, but cannot get pulled back by a sticky one.
     */
    PUSH_ONLY(net.minecraft.world.level.material.PushReaction.PUSH_ONLY);

    final net.minecraft.world.level.material.PushReaction object;

    PushReaction(net.minecraft.world.level.material.PushReaction object) {
        this.object = object;
    }
}
