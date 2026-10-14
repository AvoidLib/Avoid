package pl.olafcio.avoid.net.advancement;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;

/**
 * A namespace used for managing advancements.
 * <br/><br/>
 * Currently, the only available method is {@link #register(Identification, Class) register([Identification] id, [Class<? extends Advancement>] advancement}.
 */
@ApiStatus.Experimental
public final class Advancements {
    @ApiStatus.Internal
    private Advancements() {}

    /**
     * Creates an advancement.
     * @param id The advancement's ID. Will be visible e.g. in the {@code /advancement grant} command.
     * @param advancement The advancement's class. <b><u><i>(a public no-arg constructor has to be provided!)</i></u></b>
     */
    public static void register(Identification id, Class<? extends Advancement> advancement) {
        pl.olafcio.avoid_impl.net.advancement.Advancements.register(id, advancement);
    }
}
