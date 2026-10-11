package pl.olafcio.avoid_impl.mixinclass;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.sound.Sound;
import pl.olafcio.avoid.net.sound.category.SoundCategory;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid_impl.net.sound.category.SoundCategoryNative;

public final class NativeServerSound extends Sound {
    private final SoundEvent value;
    private final SoundSource soundSource;
    private final double d;
    private final double e;
    private final double f;
    private final float g;
    private final float h;
    private final long l;

    public NativeServerSound(SoundEvent value, SoundSource soundSource, double d, double e, double f, float g, float h, long l) {
        this.value = value;
        this.soundSource = soundSource;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        this.l = l;
    }

    @Override
    public Identification getID() {
        return IdentificationNative.convertFrom(value.location());
    }

    @Override
    public SoundCategory getCategory() {
        return SoundCategoryNative.convertFrom(soundSource);
    }

    @Override
    public float getVolume() {
        return g;
    }

    @Override
    public float getPitch() {
        return h;
    }

    @Override
    public double getX() {
        return d;
    }

    @Override
    public double getY() {
        return e;
    }

    @Override
    public double getZ() {
        return f;
    }

    // TODO getSeed
}
