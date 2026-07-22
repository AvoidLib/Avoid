package pl.olafcio.avoid.net.block.properties;

import pl.olafcio.avoid.net.block.properties.sound.SoundEvent;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _sound {
    float volume();
    float pitch();

    SoundEvent breakSound();
    SoundEvent stepSound();
    SoundEvent placeSound();
    SoundEvent hitSound();
    SoundEvent fallSound();
}
