package pl.olafcio.avoid;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.mods.AvoidModMeta;

import java.util.HashMap;

@ApiStatus.Experimental
@NullMarked
public final class AvoidManager {
    static final HashMap<String, AvoidModMeta> metadatas
           = new HashMap<>();

    private AvoidManager() {}

    // TODO: Should I add a method to get all loaded mods in the wrapped running modloader?

    public static boolean isLoadedAddon(String id) {
        return metadatas.containsKey(id);
    }

    @Nullable
    public static AvoidModMeta getLoadedAddon(String id) {
        return metadatas.get(id);
    }

    public static AvoidModMeta[] getLoadedAddons() {
        return metadatas.values().toArray(AvoidModMeta[]::new);
    }
}
