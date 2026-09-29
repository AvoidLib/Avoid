package pl.olafcio.avoid.platform;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

import java.util.Map;

/**
 * A mod/plugin or its contributor's contact information on the wrapped loader (the actual platform, e.g. Fabric/NeoForge/Paper).
 * @param map The property map of the contact information to wrap.
 */
@ApiStatus.Experimental
@Discouraged(reason = "This might be changed to be an interface or abstract class")
public record PlatformContact(Map<String, String> map) {
    @Nullable
    public String get(@NotNull String key) {
        return map.get(key);
    }

    /**
     * Should return the Discord invite link for discussions about the mod.<br/>
     * Example: {@code https://discord.gg/example}
     */
    @Nullable
    public String discord() {
        return map.get("discord");
    }

    /**
     * Should return the e-mail address for contact about the mod.<br/>
     * Example: {@code hello@example.com}
     */
    @Nullable
    public String email() {
        return map.get("email");
    }

    /**
     * Should return an IRC link for discussions about the mod.<br/>
     * Example: {@code irc://irc.esper.net:6667/charset}
     */
    @Nullable
    public String irc() {
        return map.get("irc");
    }

    /**
     * Should return an HTTP/HTTPS link for a homepage of the mod.<br/>
     * Example: {@code https://example.com}
     */
    @Nullable
    public String homepage() {
        return map.get("homepage");
    }

    /**
     * Should return an HTTP/HTTPS link for an issues page of the mod.<br/>
     * Example: {@code https://github.com/example/examplemod/issues}
     */
    @Nullable
    public String issues() {
        return map.get("issues");
    }

    /**
     * Should return an HTTP/HTTPS/VCS link for a sources page of the mod.<br/>
     * Example: {@code https://github.com/example/examplemod},<br/>&emsp;&emsp;&emsp;&emsp;&ensp;{@code https://github.com/example/examplemod.git}
     */
    @Nullable
    public String sources() {
        return map.get("sources");
    }

    /**
     * Should return an HTTP/HTTPS link to join the Slack group of the mod.
     */
    @Nullable
    public String slack() {
        return map.get("slack");
    }

    /**
     * Should return an HTTP/HTTPS link to visit the Twitter page of the author of the mod.
     */
    @Nullable
    public String twitter() {
        return map.get("twitter");
    }
}
