package pl.olafcio.avoid.net.datapack.pack;

import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "package")
@Discouraged(reason = "This may be refactored away")
public enum DatapackCompatibility {
    TOO_OLD,
    TOO_NEW,
    UNKNOWN,
    COMPATIBLE
}
