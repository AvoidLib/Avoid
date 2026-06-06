package pl.olafcio.avoid.net.nbt;

public final class NbtByte extends NbtElement implements NbtNumber {
    final byte value;

    NbtByte(byte value) {
        this.value = value;
    }

    NbtByte(short value) {
        this.value = (byte) value;
    }

    NbtByte(int value) {
        this.value = (byte) value;
    }

    public byte get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtByte(value);
    }
}
