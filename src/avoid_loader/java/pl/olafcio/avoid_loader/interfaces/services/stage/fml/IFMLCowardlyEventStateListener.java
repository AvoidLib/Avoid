package pl.olafcio.avoid_loader.interfaces.services.stage.fml;

/**
 * A service that gets loaded when the event state gets broken on Forge.<br/>
 * This is after Avoid Loader.
 * <br/><br/>
 * <b>NOTE:</b> This currently doesn't get fired on NeoForge because, guess what!<br/>&emsp;&emsp;&emsp;&ensp;I'd have to use Unsafe!
 * <br/><br/>
 * <pre>
 * {@code
 *  private static final List<ModLoadingIssue> loadingIssues = new ArrayList<>();
 * }
 * </pre>
 * <br/>
 * Thank their excellent feature breaking team for this amazing... "feature"! Of breaking reflection!
 *
 * @service
 */
public interface IFMLCowardlyEventStateListener {
    void acknowledge();
}
