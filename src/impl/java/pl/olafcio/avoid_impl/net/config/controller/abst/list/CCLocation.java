package pl.olafcio.avoid_impl.net.config.controller.abst.list;

import pl.olafcio.avoid.mods.config.ConfigurationIssue;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.location.Location;
import pl.olafcio.avoid_impl.net.config.controller.abst.AbstractConfigController;

public final class CCLocation
       extends AbstractConfigController<Location, String, String>
{
    @Override
    public Location convertFrom(String value) {
        if (!value.startsWith("Location[") || !value.endsWith("]"))
            throw new ConfigurationIssue("Expected Location, got: '%s'".formatted(value));

        var parts = value.substring(9, value.length() - 1)
                         .split(" / ", 3);

        var pos = parts[1].split(" ", 3);
        var rot = parts[2].split(" ", 2);

        return new Location(
                Identification.of(parts[0]),
                new BlockPos(
                        Integer.parseInt(pos[0]),
                        Integer.parseInt(pos[1]),
                        Integer.parseInt(pos[2])
                ),
                Float.parseFloat(rot[0]),
                Float.parseFloat(rot[1])
        );
    }

    @Override
    public String convertTo(Location value) {
        return "Location[" +
                             value.world().toString(':') + " / " +
                             value.blockPos().x() + " " + value.blockPos().y() + " " + value.blockPos().z() + " / " +
                             value.yaw() + " " + value.pitch() +
                       "]";
    }
}
