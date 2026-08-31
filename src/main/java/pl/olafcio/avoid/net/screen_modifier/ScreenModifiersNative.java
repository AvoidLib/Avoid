package pl.olafcio.avoid.net.screen_modifier;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.ArrayList;
import java.util.function.Supplier;

@Native
@ApiStatus.Internal
public final class ScreenModifiersNative {
    @ApiStatus.Internal
    private ScreenModifiersNative() {}

    public static final ArrayList<Supplier<ScreenModifier>> modifiers
                  = new ArrayList<>();

    public static void register(Supplier<ScreenModifier> supplier) {
        modifiers.add(supplier);
    }
}
