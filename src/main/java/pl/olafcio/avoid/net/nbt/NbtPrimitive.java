package pl.olafcio.avoid.net.nbt;

public sealed interface NbtPrimitive
       permits NbtString, NbtNumber
{}
