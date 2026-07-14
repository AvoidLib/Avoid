package pl.olafcio.avoid.mods.loader.mod;

import java.nio.file.Path;

sealed interface LX permits LXScreenOverwrite {
    Path mod();
}
