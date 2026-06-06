package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public final class NbtDouble extends NbtElement implements NbtNumber<Double> {
    final double value;

    NbtDouble(double value) {
        this.value = value;
    }

    public Double get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtDouble(value);
    }
}
