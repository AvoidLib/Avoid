package pl.olafcio.avoid.mods.annotation_processor;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.effect.Effects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to automatically register this class as an effect, with the {@link Effects} namespace.
 * @see pl.olafcio.avoid.net.effect.Effect net.effect\Effect
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ApiStatus.Experimental
public @interface AutoEffect {}
