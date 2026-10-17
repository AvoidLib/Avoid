package pl.olafcio.avoid.net.services.list.session;

import java.net.URI;
import java.util.Map;

public record PlayerTexture(
        URI url,
        Map<String, String> metadata
) {}
