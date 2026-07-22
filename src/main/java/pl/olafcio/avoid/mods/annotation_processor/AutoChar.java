package pl.olafcio.avoid.mods.annotation_processor;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to set the target character to the given parameter.<br/>
 * This is currently only used together with {@link AutoSelector} to set a selector's char.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
public @interface AutoChar {
    char value();
}
