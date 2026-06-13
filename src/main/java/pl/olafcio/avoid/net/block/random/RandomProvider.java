package pl.olafcio.avoid.net.block.random;

import net.minecraft.util.RandomSource;

@SuppressWarnings("ClassCanBeRecord")
public final class RandomProvider {
    private final RandomSource source;

    RandomProvider(RandomSource source) {
        this.source = source;
    }

    public void setSeed(long l) {
        source.setSeed(l);
    }

    public int nextInt() {
        return source.nextInt();
    }

    public int nextInt(int i) {
        return source.nextInt(i);
    }

    public int nextIntBetweenInclusive(int i, int j) {
        return this.nextInt(j - i + 1) + i;
    }

    public long nextLong() {
        return source.nextLong();
    }

    public boolean nextBoolean() {
        return source.nextBoolean();
    }

    public float nextFloat() {
        return source.nextFloat();
    }

    public double nextDouble() {
        return source.nextDouble();
    }

    public double nextGaussian() {
        return source.nextGaussian();
    }

    public double triangle(double d, double e) {
        return source.triangle(d, e);
    }

    public float triangle(float f, float g) {
        return source.triangle(f, g);
    }

    public void consumeCount(int i) {
        source.consumeCount(i);
    }

    public int nextInt(int i, int j) {
        return source.nextInt(i, j);
    }
}
