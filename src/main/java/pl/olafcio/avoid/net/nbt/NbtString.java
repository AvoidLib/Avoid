package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public final class NbtString
             extends NbtElement
             implements NbtPrimitive<String>
{
    final String value;

    NbtString(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

    @Override
    public NbtElement deepCopy() {
        return new NbtString(value);
    }
}
