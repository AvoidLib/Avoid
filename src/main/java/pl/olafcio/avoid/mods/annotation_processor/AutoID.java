package pl.olafcio.avoid.mods.annotation_processor;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to implicitly set the target identification based on the parent mod ID and the class name.<br/>
 * This may be used with {@link AutoBlock}, {@link AutoItem} or {@link AutoEntity}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
public @interface AutoID {}
