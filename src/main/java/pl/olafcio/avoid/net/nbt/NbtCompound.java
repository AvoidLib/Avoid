package pl.olafcio.avoid.net.nbt;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import pl.olafcio.avoid.annotations.describe.CanIgnoreReturnValue;

import java.util.Map;
import java.util.Set;

public final class NbtCompound extends NbtElement {
    private final LinkedTreeMap<String, NbtElement> members = new LinkedTreeMap<>(false);

    public NbtCompound() {}

    @Override
    public NbtCompound deepCopy() {
        NbtCompound result = new NbtCompound();

        for (Map.Entry<String, NbtElement> entry : members.entrySet()) {
            result.add(entry.getKey(), entry.getValue().deepCopy());
        }

        return result;
    }

    public void add(String property, NbtElement value) {
        members.put(property, value);
    }

    @CanIgnoreReturnValue
    public NbtElement remove(String property) {
        return members.remove(property);
    }

    public void addProperty(String property, String value) {
        add(property, new NbtString(value));
    }

    public void addProperty(String property, int value) {
        add(property, new NbtInt(value));
    }

    public void addProperty(String property, float value) {
        add(property, new NbtFloat(value));
    }

    public void addProperty(String property, short value) {
        add(property, new NbtShort(value));
    }

    public void addProperty(String property, long value) {
        add(property, new NbtLong(value));
    }

    public void addProperty(String property, double value) {
        add(property, new NbtDouble(value));
    }

    public void addProperty(String property, boolean value) {
        add(property, value ? new NbtByte(1) : new NbtByte(0));
    }

    public void addProperty(String property, Byte value) {
        add(property, new NbtByte(value));
    }

    public Set<Map.Entry<String, NbtElement>> entrySet() {
        return members.entrySet();
    }

    public Set<String> keySet() {
        return members.keySet();
    }

    public int size() {
        return members.size();
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public boolean has(String memberName) {
        return members.containsKey(memberName);
    }

    public NbtElement get(String memberName) {
        return members.get(memberName);
    }

    public NbtString getString(String memberName) {
        return (NbtString) members.get(memberName);
    }

    public NbtInt getInt(String memberName) {
        return (NbtInt) members.get(memberName);
    }

    public NbtFloat getFloat(String memberName) {
        return (NbtFloat) members.get(memberName);
    }

    public NbtShort getShort(String memberName) {
        return (NbtShort) members.get(memberName);
    }

    public NbtLong getLong(String memberName) {
        return (NbtLong) members.get(memberName);
    }

    public NbtDouble getDouble(String memberName) {
        return (NbtDouble) members.get(memberName);
    }

    public NbtByte getByte(String memberName) {
        return (NbtByte) members.get(memberName);
    }

    public boolean getBoolean(String memberName) {
        return ((NbtByte) members.get(memberName)).get() == (byte) 1;
    }

    public NbtList getList(String memberName) {
        return (NbtList) members.get(memberName);
    }

    /**
     * This is like getMap/getObject, except it's called Compound in NBT.
     */
    public NbtCompound getCompound(String memberName) {
        return (NbtCompound) members.get(memberName);
    }

    public Map<String, NbtElement> asMap() {
        // It is safe to expose the underlying map because it disallows null keys and values
        return members;
    }

    @Override
    public boolean equals(Object o) {
        return (o == this) || (o instanceof NbtCompound c && c.members.equals(members));
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}
