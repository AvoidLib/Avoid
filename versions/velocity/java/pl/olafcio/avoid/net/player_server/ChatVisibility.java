package pl.olafcio.avoid.net.player_server;

import com.velocitypowered.api.proxy.player.PlayerSettings;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.component.Components;

@NeverRemoval
public enum ChatVisibility {
    CHAT_AND_COMMANDS(0, "options.chat.visibility.full"),
    COMMAND_ONLY(1, "options.chat.visibility.system"),
    DISABLED(2, "options.chat.visibility.hidden");

    private final int id;
    private final String translation;

    ChatVisibility(int id, String translation) {
        this.id = id;
        this.translation = translation;
    }

    public int getId() {
        return id;
    }

    public String getTranslation() {
        return translation;
    }

    public BaseComponent<?> getComponent() {
        return Components.translation(translation);
    }

    public static ChatVisibility from(PlayerSettings.ChatMode from) {
        if (from == PlayerSettings.ChatMode.COMMANDS_ONLY)
            return COMMAND_ONLY;
        else if (from == PlayerSettings.ChatMode.HIDDEN)
            return DISABLED;
        else return CHAT_AND_COMMANDS;
    }
}
