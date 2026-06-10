package pl.olafcio.avoid.net.command.handling;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.command.executor.Executor;

import java.util.Map;

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
