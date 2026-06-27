package pl.olafcio.avoid.net.player;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@NeverRemoval
public record PlayerProfile(
        UUID id,
        String name,
        Map<String, Collection<?>> properties
) {}
