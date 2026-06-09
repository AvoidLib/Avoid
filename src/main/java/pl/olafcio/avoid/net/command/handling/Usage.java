package pl.olafcio.avoid.net.command.handling;

import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
public final class Usage {
    private final Map<String, Object> args;
    public Usage(Map<String, Object> args) {
        this.args = args;
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
