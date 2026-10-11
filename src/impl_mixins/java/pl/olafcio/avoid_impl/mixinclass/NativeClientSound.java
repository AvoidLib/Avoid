package pl.olafcio.avoid_impl.mixinclass;

import net.minecraft.client.resources.sounds.SoundInstance;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.sound.ClientSound;
import pl.olafcio.avoid.net.sound.SoundAttenuation;
import pl.olafcio.avoid.net.sound.category.SoundCategory;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid_impl.net.sound.category.SoundCategoryNative;

public final class NativeClientSound extends ClientSound {
    private final SoundInstance sound;

    public NativeClientSound(SoundInstance sound) {
        this.sound = sound;
    }

    @Override
    public Identification getID() {
        return IdentificationNative.convertFrom(sound.getIdentifier());
    }

    @Override
    public SoundCategory getCategory() {
        return SoundCategoryNative.convertFrom(sound.getSource());
    }

    @Override
    public boolean isLooping() {
        return sound.isLooping();
    }

    @Override
    public boolean isRelative() {
        return sound.isRelative();
    }

    @Override
    public int getDelay() {
        return sound.getDelay();
    }

    @Override
    public float getVolume() {
        return sound.getVolume();
    }

    @Override
    public float getPitch() {
        return sound.getPitch();
    }

    @Override
    public double getX() {
        return sound.getX();
    }

    @Override
    public double getY() {
        return sound.getY();
    }

    @Override
    public double getZ() {
        return sound.getZ();
    }

    @Override
    public SoundAttenuation getAttenuation() {
        return sound.getAttenuation() == SoundInstance.Attenuation.LINEAR
                    ? SoundAttenuation.LINEAR
                    : SoundAttenuation.NONE;
    }

    @Override
    public boolean canStartSilent() {
        return sound.canStartSilent();
    }

    @Override
    public boolean canPlaySound() {
        return sound.canPlaySound();
    }
}
