package pl.olafcio.avoid.net.command_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.mods.event.Cancellable;

@ApiStatus.Experimental
public final class ServerCommandExecuteEvent extends Cancellable {
    private final String input;

    public ServerCommandExecuteEvent(String input) {
        this.input = input;
    }

    @Discouraged(reason = "This method may get renamed." +
                          "To be honest you may be safer with reflection on the 'input' field.")
    public String getInput() {
        return input;
    }
}
