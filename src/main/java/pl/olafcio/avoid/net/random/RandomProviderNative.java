package pl.olafcio.avoid.net.random;

import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class RandomProviderNative {
    @ApiStatus.Internal
    private RandomProviderNative() {}

    public static RandomProvider create(RandomSource source) {
        return new RandomProvider(source);
    }

    public static RandomSource convert(pl.olafcio.avoid.net.block.random.RandomProvider provider) {
        return provider.source;
    }
}
