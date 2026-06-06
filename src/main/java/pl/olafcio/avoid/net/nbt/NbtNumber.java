package pl.olafcio.avoid.net.nbt;

public sealed interface NbtNumber
       extends NbtPrimitive
       permits NbtInt, NbtFloat, NbtShort, NbtLong, NbtDouble, NbtByte
{}
