package pl.olafcio.avoid.net.command.annotation;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a method that will be called when the provided usage is used.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
public @interface Syntax {
    String value();
}
