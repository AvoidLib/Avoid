package pl.olafcio.avoid.net.advancement;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;

@ApiStatus.Experimental
public final class Advancements {
    @ApiStatus.Internal
    private Advancements() {}

    public static void register(Identification id, Class<? extends Advancement> advancement) {
        pl.olafcio.avoid_impl.net.advancement.Advancements.register(id, advancement);
    }
}
