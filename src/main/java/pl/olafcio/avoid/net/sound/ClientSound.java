package pl.olafcio.avoid.net.sound;

public abstract class ClientSound extends Sound {
    public abstract boolean isLooping();
    public abstract boolean isRelative();

    public abstract int getDelay();

    public abstract SoundAttenuation getAttenuation();

    public boolean canStartSilent() {
        return false;
    }
    public boolean canPlaySound() {
        return true;
    }
}
