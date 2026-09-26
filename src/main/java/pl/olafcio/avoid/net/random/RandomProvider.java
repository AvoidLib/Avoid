package pl.olafcio.avoid.net.random;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public abstract class RandomProvider extends pl.olafcio.avoid.net.block.random.RandomProvider {
    @Override
    public abstract void setSeed(long l);

    @Override
    public abstract int nextInt();

    @Override
    public abstract int nextInt(int i);

    @Override
    public abstract int nextIntBetweenInclusive(int i, int j);

    @Override
    public abstract long nextLong();

    @Override
    public abstract boolean nextBoolean();

    @Override
    public abstract float nextFloat();

    @Override
    public abstract double nextDouble();

    @Override
    public abstract double nextGaussian();

    @Override
    public abstract double triangle(double d, double e);

    @Override
    public abstract float triangle(float f, float g);

    @Override
    public abstract void consumeCount(int i);

    @Override
    public abstract int nextInt(int i, int j);
}
