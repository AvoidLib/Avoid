package pl.olafcio.avoid.net.config.annotations;

import java.lang.annotation.*;

/**
 * Specifies the annotated field represents an object, that has {@link YAML @YAML} fields, and should be included as an object in the YAML output.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Nested {}
