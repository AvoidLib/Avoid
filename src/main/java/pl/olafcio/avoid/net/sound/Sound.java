package pl.olafcio.avoid.net.sound;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.sound.category.SoundCategory;

@ApiStatus.Experimental
public abstract class Sound {
    public abstract Identification getID();
    public abstract SoundCategory getCategory();

    public abstract float getVolume();
    public abstract float getPitch();

    public abstract double getX();
    public abstract double getY();
    public abstract double getZ();
}
