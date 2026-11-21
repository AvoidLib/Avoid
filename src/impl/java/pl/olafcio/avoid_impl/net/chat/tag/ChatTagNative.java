package pl.olafcio.avoid_impl.net.chat.tag;

import net.minecraft.client.GuiMessageTag;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.net.chat.tag.ChatTag;
import pl.olafcio.avoid.net.chat.tag.ChatTagIcon;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

@Native
@MCEnvironment("client")
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
