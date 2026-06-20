package pl.olafcio.avoid.net.player_server;

public record PlayerInput(
        boolean forward,
        boolean backward,
        boolean left,
        boolean right,
        boolean jump,
        boolean shift,
        boolean sprint
) {}
