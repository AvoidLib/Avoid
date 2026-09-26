package pl.olafcio.avoid.net.block.random;

import org.jetbrains.annotations.ApiStatus;

/**
 * @deprecated Use {@link pl.olafcio.avoid.net.random.RandomProvider net.random.RandomProvider} instead.
 */
@Deprecated(forRemoval = true, since = "v1.23")
@ApiStatus.NonExtendable
public abstract class RandomProvider {
    public abstract void setSeed(long l);
    public abstract int nextInt();
    public abstract int nextInt(int i);
    public abstract int nextIntBetweenInclusive(int i, int j);
    public abstract long nextLong();
    public abstract boolean nextBoolean();
    public abstract float nextFloat();
    public abstract double nextDouble();
    public abstract double nextGaussian();
    public abstract double triangle(double d, double e);
    public abstract float triangle(float f, float g);
    public abstract void consumeCount(int i);
    public abstract int nextInt(int i, int j);
}
