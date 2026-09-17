package pl.olafcio.avoid.net.chat.tag;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

/**
 * An object representing the left line of each message in a vanilla client chat.
 * <br/><br/>
 * <b>NOTE:</b> It is unrecommended to use this object, as most players use mods<br/>
 * &nbsp;&ensp;&nbsp;&nbsp;&nbsp;&ensp;&ensp;&ensp;such as No Chat Reports that hide it.
 */
@ApiStatus.Experimental
public record ChatTag(
        int indicatorColor,
        @Nullable ChatTagIcon icon,
        @Nullable BaseComponent<?> text,
        @Nullable String logTag
) {}
