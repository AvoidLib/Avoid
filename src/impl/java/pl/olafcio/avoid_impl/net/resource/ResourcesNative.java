package pl.olafcio.avoid_impl.net.resource;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.HashMap;

@Native
@ApiStatus.Internal
public final class ResourcesNative {
    @ApiStatus.Internal
    private ResourcesNative() {}

    public static final HashMap<Integer, Identifier> replacements
                  = new HashMap<>();
}
