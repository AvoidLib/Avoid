package pl.olafcio.avoid.net.chat.tag;

import net.minecraft.client.GuiMessageTag;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.IdentificationNative;

@Native
@ApiStatus.Internal
public class ChatTagNative {
    @ApiStatus.Internal
    private ChatTagNative() {}

    public static ChatTag convertFrom(GuiMessageTag tag) {
        return new ChatTag(
                tag.indicatorColor(),

                tag.icon() == null
                        ? null
                        : new ChatTagIcon(IdentificationNative.convertFrom(tag.icon().sprite), tag.icon().width, tag.icon().height),

                tag.text() == null
                        ? null
                        : COFromNative.from(tag.text()),

                tag.logTag()
        );
    }
}
