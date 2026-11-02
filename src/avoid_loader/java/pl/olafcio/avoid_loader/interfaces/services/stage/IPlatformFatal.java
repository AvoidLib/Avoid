package pl.olafcio.avoid_loader.interfaces.services.stage;

/**
 * A service that gets loaded when the platform's modloader fails critically and is going to display a GUI message about it.
 * <p>A couple notes:
 * <ul>
 *     <li>this is done <b>at best effort:</b> it may not be fired in every case.</li>
 *     <li>it may not be implemented on every platform.</li>
 * </ul>
 *
 * @service
 */
public interface IPlatformFatal {
    void acknowledge(CrashEvent event);

    interface CrashEvent {
        /**
         * Returns whether a window describing the crash should be displayed.<br/><br/>
         * <b>Note:</b> On some platforms, this always returns a fixed value.
         */
        boolean shouldDisplay();

        /**
         * Sets whether a window describing the crash should be displayed.<br/><br/>
         * <b>Note:</b> This may not work on some platforms.
         */
        void shouldDisplay(boolean value);
    }
}
