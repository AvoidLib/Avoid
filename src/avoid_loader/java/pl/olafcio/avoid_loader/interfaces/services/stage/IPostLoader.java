package pl.olafcio.avoid_loader.interfaces.services.stage;

/**
 * A service that gets loaded when the platform's preLaunch (usually {@link pl.olafcio.avoid_platform.Magician Magician}) is fired.<br/>
 * This is after Avoid Loader.
 * <br/><br/>
 *
 * <b>Note:</b> This may be fired immediately after {@link pl.olafcio.avoid_loader.PreModLoader#preload() PreModLoader.preload()} on some platforms.
 *
 * @service
 */
public interface IPostLoader {
    void acknowledge();
}
