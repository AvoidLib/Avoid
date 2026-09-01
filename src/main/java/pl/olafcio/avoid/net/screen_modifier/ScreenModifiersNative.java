package pl.olafcio.avoid.net.screen_modifier;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

@Native
@ApiStatus.Internal
public final class ScreenModifiersNative {
    @ApiStatus.Internal
    private ScreenModifiersNative() {}

    public static final HashMap<Class<? extends Screen>, ArrayList<Supplier<ScreenModifier>>> modifiers
                  = new HashMap<>();

    public static void register(Supplier<ScreenModifier> supplier, Class<? extends Screen> screen) {
        modifiers.computeIfAbsent(screen, x -> new ArrayList<>())
                 .add(supplier);
    }
}
