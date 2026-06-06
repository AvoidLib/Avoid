package pl.olafcio.avoid.net.nbt;

import com.google.gson.JsonElement;

public abstract class NbtElement extends JsonElement {
    NbtElement() {}

    @Override
    public abstract NbtElement deepCopy();
}
