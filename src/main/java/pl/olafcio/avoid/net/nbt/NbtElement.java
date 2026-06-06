package pl.olafcio.avoid.net.nbt;

import com.google.gson.JsonElement;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public abstract class NbtElement extends JsonElement {
    NbtElement() {}

    @Override
    @NeverRemoval
    public abstract NbtElement deepCopy();
}
