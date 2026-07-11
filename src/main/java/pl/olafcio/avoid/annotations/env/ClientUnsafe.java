package pl.olafcio.avoid.annotations.env;

import java.lang.annotation.*;

/**
 * Indicates a method shouldn't be used on the client, as it may create issues, and therefore undefined behaviour.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface ClientUnsafe {}
