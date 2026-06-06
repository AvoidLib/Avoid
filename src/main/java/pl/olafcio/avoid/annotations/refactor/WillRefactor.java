package pl.olafcio.avoid.annotations.refactor;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * Indicates that the annotated element may be renamed or removed at any time.
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.RECORD_COMPONENT, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@WillRefactor(aspect = "name")
@ApiStatus.Internal
@ApiStatus.NonExtendable
public @interface WillRefactor {
    /**
     * Why the element will be changed.
     */
    String reason() default "";

    /**
     * What regarding the element will be changed.<br/>
     * Should be e.g. {@code name}.
     */
    String aspect() default "";
}
