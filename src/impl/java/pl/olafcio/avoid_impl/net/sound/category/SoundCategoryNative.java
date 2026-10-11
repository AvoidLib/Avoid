package pl.olafcio.avoid_impl.net.sound.category;

import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.sound.category.SoundCategory;

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

    public static SoundCategory convertFrom(SoundSource value) {
             if (value == SoundSource.UI)      return SoundCategory.UI;
        else if (value == SoundSource.MASTER)  return SoundCategory.MASTER;
        else if (value == SoundSource.AMBIENT) return SoundCategory.AMBIENT;
        else if (value == SoundSource.BLOCKS)  return SoundCategory.BLOCKS;
        else if (value == SoundSource.HOSTILE) return SoundCategory.HOSTILE;
        else if (value == SoundSource.MUSIC)   return SoundCategory.MUSIC;
        else if (value == SoundSource.NEUTRAL) return SoundCategory.NEUTRAL;
        else if (value == SoundSource.PLAYERS) return SoundCategory.PLAYERS;
        else if (value == SoundSource.RECORDS) return SoundCategory.RECORDS;
        else if (value == SoundSource.VOICE)   return SoundCategory.VOICE;
        else if (value == SoundSource.WEATHER) return SoundCategory.WEATHER;
        else
            throw new RuntimeException("Unrecognized sound source '%s'".formatted(value.name()));
    }
}
