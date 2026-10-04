package pl.olafcio.avoid.annotations.describe;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

import java.lang.annotation.*;

/**
 * Indicates that the return value of the method may be ignored.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@WillRefactor(aspect = "name")
@ApiStatus.Internal
@ApiStatus.NonExtendable
public @interface CanIgnoreReturnValue {}
