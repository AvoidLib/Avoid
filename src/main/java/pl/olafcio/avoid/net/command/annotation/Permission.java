package pl.olafcio.avoid.net.command.annotation;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies a syntax method's permission atom.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
public @interface Permission {
    String value();
}
