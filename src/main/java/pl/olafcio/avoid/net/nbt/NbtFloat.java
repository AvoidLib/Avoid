package pl.olafcio.avoid.net.nbt;

public final class NbtFloat extends NbtElement implements NbtNumber {
    final float value;

    NbtFloat(float value) {
        this.value = value;
    }

    public float get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtFloat(value);
    }
}
