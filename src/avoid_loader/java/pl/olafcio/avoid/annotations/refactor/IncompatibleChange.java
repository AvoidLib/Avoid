package pl.olafcio.avoid.annotations.refactor;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.RECORD_COMPONENT, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
@WillRefactor(aspect = "name")
@ApiStatus.Internal
@ApiStatus.NonExtendable
public @interface IncompatibleChange {
    String reason();
    String change();
    String since();
}
