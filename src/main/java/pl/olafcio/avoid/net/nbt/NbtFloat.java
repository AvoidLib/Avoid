package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public final class NbtFloat extends NbtElement implements NbtNumber<Float> {
    final float value;

    NbtFloat(float value) {
        this.value = value;
    }

    public Float get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtFloat(value);
    }
}
