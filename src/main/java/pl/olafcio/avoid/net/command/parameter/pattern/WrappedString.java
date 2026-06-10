package pl.olafcio.avoid.net.command.parameter.pattern;

// I need the hashcode to be unique per object

@SuppressWarnings("ClassCanBeRecord")
public final class WrappedString {
    private final String text;

    public WrappedString(String text) {
        this.text = text;
    }

    public String string() {
        return text;
    }

    @Override
    public String toString() {
        return "WrappedString[" +
                "text=" + text + ']';
    }
}
