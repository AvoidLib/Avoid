package pl.olafcio.avoid_impl.net.config.controller.abst.list;

import pl.olafcio.avoid.mods.config.ConfigurationIssue;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.block_location.BlockLocation;
import pl.olafcio.avoid_impl.net.config.controller.abst.AbstractConfigController;

public final class CCBlockLocation
       extends AbstractConfigController<BlockLocation, String, String>
{
    @Override
    public BlockLocation convertFrom(String value) {
        if (!value.startsWith("BlockLocation[") || !value.endsWith("]"))
            throw new ConfigurationIssue("Expected BlockLocation, got: '%s'".formatted(value));

        var parts = value.substring(14, value.length() - 1)
                         .split(" / ", 2);

        var values = parts[1].split(" ", 3);

        return new BlockLocation(
                Identification.of(parts[0]),
                new BlockPos(
                        Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2])
                )
        );
    }

    @Override
    public String convertTo(BlockLocation value) {
        return "BlockLocation[" +
                                 value.world().toString(':') + " / " +
                                 value.blockPos().x() + " " + value.blockPos().y() + " " + value.blockPos().z() +
                            "]";
    }
}
