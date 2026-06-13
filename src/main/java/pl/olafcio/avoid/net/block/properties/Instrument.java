package pl.olafcio.avoid.net.block.properties;

import pl.olafcio.avoid.net.block.values.NoteBlockInstrument;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Instrument {
    NoteBlockInstrument value();
}
