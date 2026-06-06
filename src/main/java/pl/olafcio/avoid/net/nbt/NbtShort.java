package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public final class NbtShort extends NbtElement implements NbtNumber<Short> {
    final short value;

    NbtShort(short value) {
        this.value = value;
    }

    public Short get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtShort(value);
    }
}
