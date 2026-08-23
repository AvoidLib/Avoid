package pl.olafcio.avoid.net.effect.instance;

import org.jetbrains.annotations.Range;
import pl.olafcio.avoid.net.id.Identification;

public final class EffectInstance {
    private final Identification id;
    private final int duration;
    private final int level;

    public EffectInstance(Identification id, int duration, @Range(from = 1, to = 256) int level) {
        this.id = id;
        this.duration = duration;
        this.level = level;
    }

    public Identification getID() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public int getLevel() {
        return level;
    }
}
