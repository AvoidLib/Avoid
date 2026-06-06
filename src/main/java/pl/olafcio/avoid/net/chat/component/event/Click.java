package pl.olafcio.avoid.net.chat.component.event;

import net.minecraft.core.Holder;
import net.minecraft.server.dialog.Dialog;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.nbt.NbtElement;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

@NullMarked
public sealed interface Click {
    Type type();

    enum Type {
        START_URL("open_url", true),
        START_FILE("open_file", false),
        CHAT_COMMAND("run_command", true),
        CHAT_SUGGEST("suggest_command", true),
        OPEN_DIALOG("show_dialog", true),
        SWITCH_PAGE("change_page", true),
        COPY("copy_to_clipboard", true),
        MISC("custom", true);

        public final String id;
        public final boolean allowInNetworking;

        Type(String id, boolean allowInNetworking) {
            this.id = id;
            this.allowInNetworking = allowInNetworking;
        }
    }

    record StartURL(URI uri) implements Click {
        @Override
        public Type type() {
            return Type.START_URL;
        }
    }

    record StartFile(String path) implements Click {
        public StartFile(File file) {
            this(file.toString());
        }

        public StartFile(Path path) {
            this(path.toFile());
        }

        public File file() {
            return new File(this.path);
        }

        @Override
        public Type type() {
            return Type.START_FILE;
        }

    }

    record ChatCommand(String command) implements Click {
        @Override
        public Type type() {
            return Type.CHAT_COMMAND;
        }
    }

    record ChatSuggest(String command) implements Click {
        @Override
        public Type type() {
            return Type.CHAT_SUGGEST;
        }
    }

    record OpenDialog(Holder<Dialog> dialog) implements Click {
        @Override
        public Type type() {
            return Type.OPEN_DIALOG;
        }
    }

    record SwitchPage(int page) implements Click {
        @Override
        public Type type() {
            return Type.SWITCH_PAGE;
        }
    }

    record Copy(String value) implements Click {
        @Override
        public Type type() {
            return Type.COPY;
        }
    }

    record Misc(Identification id, @Nullable NbtElement payload) implements Click {
        @Override
        public Type type() {
            return Type.MISC;
        }
    }
}
