package pl.olafcio.avoid_impl.mods.loader.mod;

import java.nio.file.Path;

sealed interface LX permits LXScreenOverwrite, LXScreenModifier {
    Path mod();
}
