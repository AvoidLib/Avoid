package pl.olafcio.avoid.net.services.list;

import java.util.UUID;

public abstract class PlayerQueryService {
    public abstract String resolve(UUID uuid);
    public abstract UUID resolve(String nick);
}
