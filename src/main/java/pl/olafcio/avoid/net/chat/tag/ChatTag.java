package pl.olafcio.avoid.net.chat.tag;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

@ApiStatus.Experimental
public record ChatTag(
        int indicatorColor,
        @Nullable ChatTagIcon icon,
        @Nullable BaseComponent<?> text,
        @Nullable String logTag
) {}
