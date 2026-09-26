package pl.olafcio.avoid_impl.net.random;

import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class RandomProvider extends pl.olafcio.avoid.net.random.RandomProvider {
    @ApiStatus.Internal
    public final RandomSource source;

    @ApiStatus.Internal
    public RandomProvider(Object source) {
        this.source = (RandomSource) source;
    }

    @Override
    public void setSeed(long l) {
        source.setSeed(l);
    }

    @Override
    public int nextInt() {
        return source.nextInt();
    }

    @Override
    public int nextInt(int i) {
        return source.nextInt(i);
    }

    @Override
    public int nextIntBetweenInclusive(int i, int j) {
        return this.nextInt(j - i + 1) + i;
    }

    @Override
    public long nextLong() {
        return source.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return source.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return source.nextFloat();
    }

    @Override
    public double nextDouble() {
        return source.nextDouble();
    }

    @Override
    public double nextGaussian() {
        return source.nextGaussian();
    }

    @Override
    public double triangle(double d, double e) {
        return source.triangle(d, e);
    }

    @Override
    public float triangle(float f, float g) {
        return source.triangle(f, g);
    }

    @Override
    public void consumeCount(int i) {
        source.consumeCount(i);
    }

    @Override
    public int nextInt(int i, int j) {
        return source.nextInt(i, j);
    }
}
