package pl.olafcio.avoid.annotations.threadsafe;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.*;

/**
 * Indicates that the annotated method may have issues if run from another thread.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
@ApiStatus.NonExtendable
public @interface NotThreadSafe {}
