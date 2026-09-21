package pl.olafcio.avoid;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import pl.olafcio.avoid_common.LateInitializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Avoid extends LateInitializer {
    @ApiStatus.Internal
    public static final Logger LOGGER
                      = LogUtils.getLogger();

    @ApiStatus.Internal
    public static final Avoid INSTANCE
                  = new Avoid();

    private static final String VERSION;

    public static String getVersion() {
        return VERSION;
    }

    static {
        try (var stream = Avoid.class.getResourceAsStream("/.version")) {
            VERSION = new String(stream.readAllBytes(), StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            throw new RuntimeException("AvoidLib failed to get its version", e);
        }
    }

    private Avoid() {}

    public void onInitialize() {
        pl.olafcio.avoid_impl.Avoid.INSTANCE.onInitialize();
    }

    public void onEarlyInit() {
        pl.olafcio.avoid_impl.Avoid.INSTANCE.onEarlyInit();
    }
}
