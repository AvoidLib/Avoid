package pl.olafcio.avoid.net.chat;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.annotations.threadsafe.ThreadSafe;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

@ApiStatus.Experimental
public final class Chat {
    @ApiStatus.Internal
    private Chat() {}

    public static void sendMessage(String data) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#sendMessage from the server!");

        ChatNative.sendToServer(data);
    }

    @ThreadSafe
    public static void receiveMessage(BaseComponent<?> data) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#receiveMessage from the server!");

        ChatNative.sendToClient(data);
    }
}
