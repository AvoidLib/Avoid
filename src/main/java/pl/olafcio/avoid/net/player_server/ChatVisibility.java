package pl.olafcio.avoid.net.player_server;

import net.minecraft.world.entity.player.ChatVisiblity;
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

    public static ChatVisibility from(ChatVisiblity from) {
        if (from == ChatVisiblity.SYSTEM)
            return COMMAND_ONLY;
        else if (from == ChatVisiblity.HIDDEN)
            return DISABLED;
        else return CHAT_AND_COMMANDS;
    }
}
