package pl.olafcio.avoid.net.chat.tag;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;

@ApiStatus.Experimental
public record ChatTagIcon(Identification id, int width, int height) {}
