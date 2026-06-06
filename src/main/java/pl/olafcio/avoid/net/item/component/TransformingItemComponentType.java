package pl.olafcio.avoid.net.item.component;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@ApiStatus.NonExtendable
public abstract class TransformingItemComponentType<I, O> extends ItemComponentType<O> {
    public abstract O transform(I value);
    public abstract I untransform(O value);
}
