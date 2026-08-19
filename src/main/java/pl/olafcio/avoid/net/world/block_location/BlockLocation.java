package pl.olafcio.avoid.net.world.block_location;

import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.id.Identification;

public record BlockLocation(Identification world, BlockPos blockPos) {}
