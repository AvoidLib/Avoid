package pl.olafcio.avoid.net.nbt;

public final class NbtLong extends NbtElement implements NbtNumber {
    final long value;

    NbtLong(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtLong(value);
    }
}
