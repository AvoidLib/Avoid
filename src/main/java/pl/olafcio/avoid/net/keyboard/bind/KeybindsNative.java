package pl.olafcio.avoid.net.keyboard.bind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.keyboard.Keyboard;

import java.util.ArrayList;
import java.util.function.Supplier;

@Native
@NullMarked
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public class KeybindsNative {
    @ApiStatus.Internal
    private KeybindsNative() {}

    public static final ArrayList<KeyMapping> keymappings
                  = new ArrayList<>();

    public static Supplier<Boolean> register(
            String displayName,
            @MagicConstant(valuesFromClass = Keyboard.class) int key,
            Category category
    ) {
        var keymap = new KeyMapping(displayName, key, category.category.category());

        keymappings.add(keymap);

        return keymap::isDown;
    }
}
