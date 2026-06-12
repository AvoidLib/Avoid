package pl.olafcio.avoid.net.chat_server;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;

@ApiStatus.Experimental
public final class ServerChat {
    @ApiStatus.Internal
    private ServerChat() {}

    public static void broadcast(BaseComponent<?> data) {
        var server = AvoidInternal.getServer();
        if (server == null)
            throw new ImproperEnvironment("A server is not running!");

        server.getPlayerList().broadcastSystemMessage(COToNative.from(data), false);
    }
}
