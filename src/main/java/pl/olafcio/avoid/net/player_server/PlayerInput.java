package pl.olafcio.avoid.net.player_server;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public record PlayerInput(
        boolean forward,
        boolean backward,
        boolean left,
        boolean right,
        boolean jump,
        boolean shift,
        boolean sprint
) {}
