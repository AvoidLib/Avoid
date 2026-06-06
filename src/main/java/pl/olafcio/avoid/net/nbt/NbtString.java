package pl.olafcio.avoid.net.nbt;

public final class NbtString
             extends NbtElement
             implements NbtPrimitive
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
