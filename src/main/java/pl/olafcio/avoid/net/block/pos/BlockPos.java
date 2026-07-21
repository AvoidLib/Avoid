package pl.olafcio.avoid.net.block.pos;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public record BlockPos(int x, int y, int z) {}
