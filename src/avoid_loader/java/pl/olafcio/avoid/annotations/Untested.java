package pl.olafcio.avoid.annotations;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated element has not been tested and may not work or have bugs.<br/>
 * A released version shouldn't contain any uses of this annotation.
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.RECORD_COMPONENT, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@WillRefactor(aspect = "name")
@ApiStatus.Internal
@ApiStatus.NonExtendable
public @interface Untested {
    String specifically() default "";
}
