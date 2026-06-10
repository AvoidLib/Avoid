package pl.olafcio.avoid.net.command.handling;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.command.annotation.Syntax;
import pl.olafcio.avoid.net.command.annotation.Unknown;
import pl.olafcio.avoid.net.command.executor.Executor;

import java.util.Map;

/**
 * An object containing information about the current running of a command.
 * <br/><br/>
 * When the user sends a command, like {@code /echo}, a new object is created - {@link Usage}.<br/>
 * This object is then provided to a <a style="color: #3887a1">@{@linkplain Syntax}</a> or
 * <a style="color: #3887a1">@{@linkplain Unknown}</a> method (if one has been found).
 */
@SuppressWarnings("ClassCanBeRecord")
public final class Usage {
    private final Map<String, Object> args;
    private final @Nullable Executor executor;

    public Usage(Map<String, Object> args, @Nullable Executor executor) {
        this.args = args;
        this.executor = executor;
    }

    @SuppressWarnings({"unchecked", "unused"})
    public <T> T getArgument(String name, Class<T> type) {
        return (T) args.get(name);
    }

    @Override
    public String toString() {
        return "Usage[" +
                "args=" + args + ']';
    }
}
