package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "name")
public sealed interface NbtNumber<T>
       extends NbtPrimitive<T>
       permits NbtInt, NbtFloat, NbtShort, NbtLong, NbtDouble, NbtByte
{}
