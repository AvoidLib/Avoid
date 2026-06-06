package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public final class NbtByte extends NbtElement implements NbtNumber<Byte> {
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

    public Byte get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtByte(value);
    }
}
