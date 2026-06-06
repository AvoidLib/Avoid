package pl.olafcio.avoid.net.item.component;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@ApiStatus.NonExtendable
public interface TransformingItemComponentValue<Minecraft, Avoid> {
    Avoid transform(Minecraft value);
    Minecraft untransform(Avoid value);
}
