package pl.olafcio.avoid.net.nbt;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "name")
public sealed interface NbtPrimitive<T>
       permits NbtString, NbtNumber
{
    @NeverRemoval
    T get();
}
