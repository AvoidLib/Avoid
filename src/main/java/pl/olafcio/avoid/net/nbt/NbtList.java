package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.util.LinkedList;
import java.util.List;

@NeverRemoval
public final class NbtList extends NbtElement {
    private final LinkedList<NbtElement> members = new LinkedList<>();

    NbtList() {}

    public void append(NbtElement element) {
        members.add(element);
    }

    public List<NbtElement> asList() {
        return members;
    }

    @Override
    public NbtElement deepCopy() {
        NbtList result = new NbtList();

        for (NbtElement entry : members) {
            result.members.add(entry.deepCopy());
        }

        return result;
    }
}
