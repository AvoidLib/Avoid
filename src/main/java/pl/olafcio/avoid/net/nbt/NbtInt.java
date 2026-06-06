package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public final class NbtInt
             extends NbtElement
             implements NbtNumber<Integer>
{
    final int value;

    NbtInt(int value) {
        this.value = value;
    }

    public Integer get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtInt(value);
    }
}
