package pl.olafcio.avoid.net.chat.component;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.event.Click;
import pl.olafcio.avoid.net.chat.component.event.Hover;
import pl.olafcio.avoid.net.id.Identification;

import java.awt.*;

@ApiStatus.Experimental
public final class ChatStyle {
    public Color color = null;
    public Color shadow = null;

    public Boolean bold = null;
    public Boolean italic = null;
    public Boolean underlined = null;
    public Boolean strikethrough = null;
    public Boolean obfuscated = null;

    public Identification font = null;

    public Click click = null;
    public Hover hover = null;
}
