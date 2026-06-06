package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public final class NbtLong extends NbtElement implements NbtNumber<Long> {
    final long value;

    NbtLong(long value) {
        this.value = value;
    }

    public Long get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtLong(value);
    }
}
