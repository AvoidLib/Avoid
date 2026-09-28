package pl.olafcio.avoid.platform;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

import java.util.Map;

@ApiStatus.Experimental
@Discouraged(reason = "This might be changed to be an interface or abstract class")
public record PlatformContact(Map<String, String> map) {
    @Nullable
    public String get(@NotNull String key) {
        return map.get(key);
    }

    @Nullable
    public String discord() {
        return map.get("discord");
    }

    @Nullable
    public String email() {
        return map.get("email");
    }

    @Nullable
    public String irc() {
        return map.get("irc");
    }

    @Nullable
    public String homepage() {
        return map.get("homepage");
    }

    @Nullable
    public String issues() {
        return map.get("issues");
    }

    @Nullable
    public String sources() {
        return map.get("sources");
    }

    @Nullable
    public String slack() {
        return map.get("slack");
    }

    @Nullable
    public String twitter() {
        return map.get("twitter");
    }
}
