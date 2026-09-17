package pl.olafcio.avoid.net.block.pos;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

/**
 * A block position.
 * <br/><br/>
 * This, compared to the other position objects, is made of XYZ integers.<br/>
 * Pretty much a 3-integer struct.
 */
@NeverRemoval
public record BlockPos(int x, int y, int z) {}
