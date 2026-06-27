package pl.olafcio.avoid.mods.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.*;

/**
 * Indicates the annotated method should be called automatically when the event (described by the first and only parameter)
 * is fired.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NeverRemoval
public @interface EventHandler {
    boolean ignoreCancelled() default false;
}
