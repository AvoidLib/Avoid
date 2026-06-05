package pl.olafcio.avoidlib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated element may be renamed or removed at any time.
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
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
