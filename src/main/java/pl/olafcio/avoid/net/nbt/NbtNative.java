package pl.olafcio.avoid.net.nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.Map;

@Native
@ApiStatus.Internal
public final class NbtNative {
    @ApiStatus.Internal
    private NbtNative() {}

    public static Tag convert(NbtElement element) {
        return switch (element) {
            case NbtCompound cast -> {
                var tag = new CompoundTag();
                for (Map.Entry<String, NbtElement> entry : cast.entrySet())
                    tag.put(entry.getKey(), convert(entry.getValue()));

                yield tag;
            }

            case NbtList cast -> {
                var tag = new ListTag();
                for (NbtElement item : cast.asList())
                    tag.add(convert(item));

                yield tag;
            }

            case NbtInt cast -> IntTag.valueOf(cast.get());
            case NbtShort cast -> ShortTag.valueOf(cast.get());
            case NbtLong cast -> LongTag.valueOf(cast.get());
            case NbtDouble cast -> DoubleTag.valueOf(cast.get());
            case NbtFloat cast -> FloatTag.valueOf(cast.get());
            case NbtByte cast -> ByteTag.valueOf(cast.get());
            case NbtString cast -> StringTag.valueOf(cast.get());

            default ->
                throw new RuntimeException("Unrecognized Avoid NBT element '" + element + "'");
        };
    }

    public static Tag convertJSON(JsonElement element) {
        return switch (element) {
            case JsonObject cast -> {
                var tag = new CompoundTag();
                for (Map.Entry<String, JsonElement> entry : cast.entrySet())
                    tag.put(entry.getKey(), convertJSON(entry.getValue()));

                yield tag;
            }

            case JsonArray cast -> {
                var tag = new ListTag();
                for (JsonElement item : cast.asList())
                    tag.add(convertJSON(item));

                yield tag;
            }

            case JsonPrimitive cast -> {
                if (cast.isNumber()) {
                    var num = cast.getAsNumber();
                    if (num instanceof Integer i)
                        yield IntTag.valueOf(i);
                    else if (num instanceof Float f)
                        yield FloatTag.valueOf(f);
                    else if (num instanceof Long l)
                        yield LongTag.valueOf(l);
                    else if (num instanceof Short s)
                        yield ShortTag.valueOf(s);
                    else if (num instanceof Double d)
                        yield DoubleTag.valueOf(d);
                    else if (num instanceof Byte b)
                        yield ByteTag.valueOf(b);
                } else if (cast.isString()) {
                    yield StringTag.valueOf(cast.getAsString());
                } else if (cast.isBoolean()) {
                    yield ByteTag.valueOf(cast.getAsBoolean());
                }

                throw new RuntimeException("Unserializable JSON to NBT element '" + element + "'");
            }

            default ->
                    throw new RuntimeException("Unserializable JSON to NBT element '" + element + "'");
        };
    }

    public static NbtElement convertFrom(Tag element) {
        return switch (element) {
            case CompoundTag cast -> {
                var tag = new NbtCompound();
                for (Map.Entry<String, Tag> entry : cast.entrySet())
                    tag.add(entry.getKey(), convertFrom(entry.getValue()));

                yield tag;
            }

            case ListTag cast -> {
                var tag = new NbtList();
                for (Tag item : cast)
                    tag.append(convertFrom(item));

                yield tag;
            }

            case IntTag cast -> new NbtInt(cast.value());
            case ShortTag cast -> new NbtShort(cast.value());
            case LongTag cast -> new NbtLong(cast.value());
            case DoubleTag cast -> new NbtDouble(cast.value());
            case FloatTag cast -> new NbtFloat(cast.value());
            case ByteTag cast -> new NbtByte(cast.value());
            case StringTag cast -> new NbtString(cast.value());

            default ->
                    throw new RuntimeException("Unrecognized minecraft NBT element '" + element + "'");
        };
    }
}
