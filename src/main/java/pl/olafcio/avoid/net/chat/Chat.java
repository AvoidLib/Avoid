package pl.olafcio.avoid.net.chat;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

@NeverRemoval
public final class Chat {
    @ApiStatus.Internal
    private Chat() {}

    public static void sendMessage(String data) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#sendMessage from the server!");

        ChatNative.sendToServer(data);
    }

    public static void receiveMessage(BaseComponent<?> data) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot Chat#receiveMessage from the server!");

        ChatNative.sendToClient(data);
    }
}
