package pl.olafcio.avoid.net.command_server.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.command_server.suggestions.Suggestion;

import java.util.List;

@ApiStatus.Experimental
public final class ServerCommandSuggestEvent extends Cancellable {
    private final String input;
    private final List<Suggestion> suggestions;

    public ServerCommandSuggestEvent(String input, List<Suggestion> suggestions) {
        this.input = input;
        this.suggestions = suggestions;
    }

    @Discouraged(reason = "This method may get renamed." +
                          "To be honest you may be safer with reflection on the 'input' field.")
    public String getInput() {
        return input;
    }

    @Discouraged(reason = "This method may get renamed." +
                          "To be honest you may be safer with reflection on the 'suggestions' field.")
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }
}
