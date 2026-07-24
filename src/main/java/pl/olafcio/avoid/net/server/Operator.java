package pl.olafcio.avoid.net.server;

import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

@ApiStatus.Experimental
public record Operator(String nick, UUID uuid) {}
