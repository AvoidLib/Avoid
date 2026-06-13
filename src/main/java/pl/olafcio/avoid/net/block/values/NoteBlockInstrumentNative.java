package pl.olafcio.avoid.net.block.values;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class NoteBlockInstrumentNative {
    @ApiStatus.Internal
    private NoteBlockInstrumentNative() {}

    public static net.minecraft.world.level.block.state.properties.NoteBlockInstrument convert(NoteBlockInstrument avoid) {
        return NoteBlockInstrument.getObject(avoid);
    }
}
