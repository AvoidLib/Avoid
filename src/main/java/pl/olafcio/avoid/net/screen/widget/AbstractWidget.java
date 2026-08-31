package pl.olafcio.avoid.net.screen.widget;

public abstract class AbstractWidget implements Widget {
    private boolean focused = false;

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }
}
