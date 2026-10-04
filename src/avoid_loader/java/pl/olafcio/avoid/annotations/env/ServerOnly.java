package pl.olafcio.avoid.annotations.env;

import java.lang.annotation.*;

/**
 * Indicates a method only works on the object if it's created by a dedicated/integrated server,
 * or if the object isn't associated, a dedicated or integrated server is running.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface ServerOnly {}
