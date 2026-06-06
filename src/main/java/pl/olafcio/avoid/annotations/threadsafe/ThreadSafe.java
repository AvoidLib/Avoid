package pl.olafcio.avoid.annotations.threadsafe;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.*;

/**
 * Indicates that the annotated method can be run from any thread.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
@ApiStatus.NonExtendable
public @interface ThreadSafe {}
