package pl.olafcio.avoid.net.nbt;

public final class NbtInt extends NbtElement implements NbtNumber {
    final int value;

    NbtInt(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtInt(value);
    }
}
