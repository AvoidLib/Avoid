package pl.olafcio.avoid_impl.net.config.controller.abst.list;

import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid_impl.net.config.controller.abst.AbstractConfigController;

public final class CCBlockPos
       extends AbstractConfigController<BlockPos, String, String>
{
    @Override
    public BlockPos convertFrom(String value) {
        var values = value.split(" ", 3);

        return new BlockPos(
                Integer.parseInt(values[0]),
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2])
        );
    }

    @Override
    public String convertTo(BlockPos value) {
        return value.x() + " " + value.y() + " " + value.z();
    }
}
