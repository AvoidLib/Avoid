package pl.olafcio.avoid.net.nbt;

public final class NbtShort extends NbtElement implements NbtNumber {
    final short value;

    NbtShort(short value) {
        this.value = value;
    }

    public short get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtShort(value);
    }
}
