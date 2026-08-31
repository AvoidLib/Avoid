package pl.olafcio.avoid.net.screen.widget.able;

/**
 * Indicates the element can be selected by using the {@code TAB} key.
 */
public interface Selectable extends Focusable {
    default int getTabIndex() {
        return 0;
    }
}
