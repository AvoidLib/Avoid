package pl.olafcio.avoid.net.world.location;

import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.id.Identification;

public record Location(Identification world, BlockPos blockPos, float yaw, float pitch) {}
