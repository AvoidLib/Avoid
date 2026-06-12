package pl.olafcio.avoid.net.chat.tag;

import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

public record ChatTag(
        int indicatorColor,
        @Nullable ChatTagIcon icon,
        @Nullable BaseComponent<?> text,
        @Nullable String logTag
) {}
