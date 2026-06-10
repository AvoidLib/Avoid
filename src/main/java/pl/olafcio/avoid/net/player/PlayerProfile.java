package pl.olafcio.avoid.net.player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public record PlayerProfile(
        UUID id,
        String name,
        Map<String, Collection<?>> properties
) {}
