package pl.olafcio.avoid_impl.net.block.values;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.block.values.NoteBlockInstrument;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class NoteBlockInstrumentNative {
    @ApiStatus.Internal
    private NoteBlockInstrumentNative() {}

    private static final HashMap<NoteBlockInstrument, net.minecraft.world.level.block.state.properties.NoteBlockInstrument> MAP
                   = new HashMap<>();

    public static net.minecraft.world.level.block.state.properties.NoteBlockInstrument convert(NoteBlockInstrument avoid) {
        return MAP.get(avoid);
    }

    public static void clinit() {
        var avoids = NoteBlockInstrument.values();
        var crafts = net.minecraft.world.level.block.state.properties.NoteBlockInstrument.values();

        for (var avoid : avoids) {
            for (var craft : crafts)
                if (craft.getSerializedName().equals(avoid.chime))
                    MAP.put(avoid, craft);

            for (var craft : crafts)
                if (craft.ordinal() == avoid.ordinal())
                    MAP.put(avoid, craft);
        }
    }
}
