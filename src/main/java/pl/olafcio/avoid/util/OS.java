package pl.olafcio.avoid.util;

import net.minecraft.util.Util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class OS {
    //TODO Make predefined presets, like Windows
    private OS() {}

    public static OS getOS() {
        return new OS() {
            @Override
            public void openURL(String url) {
                Util.getPlatform().openUri(URI.create(url));
            }

            @Override
            public void openURL(URL url) {
                try {
                    Util.getPlatform().openUri(url.toURI());
                } catch (URISyntaxException e) {
                    throw new RuntimeException("[OS#openURL] Invalid URL: '%s'".formatted(url), e);
                }
            }

            @Override
            public void openURL(URI url) {
                Util.getPlatform().openUri(url);
            }
        };
    }

    public abstract void openURL(URI url);
    public abstract void openURL(URL url);
    public abstract void openURL(String url);
}
