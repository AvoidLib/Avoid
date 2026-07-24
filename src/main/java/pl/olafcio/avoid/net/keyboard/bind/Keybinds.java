package pl.olafcio.avoid.net.keyboard.bind;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.net.keyboard.Keyboard;

import java.util.function.Supplier;

@NullMarked
@ApiStatus.Experimental
public final class Keybinds {
    @ApiStatus.Internal
    private Keybinds() {}

    /**
     * Registers a keybind onto the controls screen.
     * @param displayName The keybind display name translation key.
     * @param key The key code.
     * @param category The category of the key.
     * @return A supplier that returns whether the key is held.
     *         Always returns {@code false} if not on the client.
     */
    public static Supplier<Boolean> register(String displayName, @MagicConstant(valuesFromClass = Keyboard.class) int key, Category category) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            return KeybindsNative.register(displayName, key, category);

        return () -> false;
    }
}
