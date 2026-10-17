package pl.olafcio.avoid.net.services;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.services.list.PlayerQueryService;
import pl.olafcio.avoid.net.services.list.PlayerSessionService;

public abstract class Services {
    @NotNull
    public abstract PlayerQueryService playerQuery();

    @NotNull
    public abstract PlayerSessionService playerSession();
}
