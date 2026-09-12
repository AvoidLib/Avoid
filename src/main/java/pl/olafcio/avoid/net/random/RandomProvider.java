package pl.olafcio.avoid.net.random;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public final class RandomProvider extends pl.olafcio.avoid.net.block.random.RandomProvider {
    @ApiStatus.Internal
    public RandomProvider(Object source) {
        super(source);
    }

    @Override
    public void setSeed(long l) {
        super.setSeed(l);
    }

    @Override
    public int nextInt() {
        return super.nextInt();
    }

    @Override
    public int nextInt(int i) {
        return super.nextInt(i);
    }

    @Override
    public int nextIntBetweenInclusive(int i, int j) {
        return super.nextIntBetweenInclusive(i, j);
    }

    @Override
    public long nextLong() {
        return super.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return super.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return super.nextFloat();
    }

    @Override
    public double nextDouble() {
        return super.nextDouble();
    }

    @Override
    public double nextGaussian() {
        return super.nextGaussian();
    }

    @Override
    public double triangle(double d, double e) {
        return super.triangle(d, e);
    }

    @Override
    public float triangle(float f, float g) {
        return super.triangle(f, g);
    }

    @Override
    public void consumeCount(int i) {
        super.consumeCount(i);
    }

    @Override
    public int nextInt(int i, int j) {
        return super.nextInt(i, j);
    }
}
