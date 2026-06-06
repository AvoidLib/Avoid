package pl.olafcio.avoid.net.nbt;

public final class NbtDouble extends NbtElement implements NbtNumber {
    final double value;

    NbtDouble(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtDouble(value);
    }
}
