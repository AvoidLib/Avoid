package pl.olafcio.avoid.net.sound.category;

import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class SoundCategoryNative {
    @ApiStatus.Internal
    private SoundCategoryNative() {}

    public static SoundSource convert(SoundCategory value) {
             if (value == SoundCategory.UI)      return SoundSource.UI;
        else if (value == SoundCategory.MASTER)  return SoundSource.MASTER;
        else if (value == SoundCategory.AMBIENT) return SoundSource.AMBIENT;
        else if (value == SoundCategory.BLOCKS)  return SoundSource.BLOCKS;
        else if (value == SoundCategory.HOSTILE) return SoundSource.HOSTILE;
        else if (value == SoundCategory.MUSIC)   return SoundSource.MUSIC;
        else if (value == SoundCategory.NEUTRAL) return SoundSource.NEUTRAL;
        else if (value == SoundCategory.PLAYERS) return SoundSource.PLAYERS;
        else if (value == SoundCategory.RECORDS) return SoundSource.RECORDS;
        else if (value == SoundCategory.VOICE)   return SoundSource.VOICE;
        else if (value == SoundCategory.WEATHER) return SoundSource.WEATHER;
        else
            throw new RuntimeException("Unrecognized sound category '%s'".formatted(value.name()));
    }
}
