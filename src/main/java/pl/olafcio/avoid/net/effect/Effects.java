package pl.olafcio.avoid.net.effect;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.id.Identification;

public final class Effects {
    @ApiStatus.Internal
    private Effects() {}

    @Nullable
    public static Effect get(Identification id) {
        return pl.olafcio.avoid_impl.net.effect.Effects.get(id);
    }

    public static void register(Identification id, Effect effect) {
        pl.olafcio.avoid_impl.net.effect.Effects.register(id, effect);
    }
}
