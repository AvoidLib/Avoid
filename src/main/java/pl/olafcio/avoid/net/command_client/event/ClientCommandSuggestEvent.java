package pl.olafcio.avoid.net.command_client.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.net.command_client.suggestion.ClientSuggestion;

import java.util.List;

@ApiStatus.Experimental
public final class ClientCommandSuggestEvent {
    private final String input;
    private final List<ClientSuggestion> suggestions;

    public ClientCommandSuggestEvent(String input, List<ClientSuggestion> suggestions) {
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
    public List<ClientSuggestion> getSuggestions() {
        return suggestions;
    }
}
