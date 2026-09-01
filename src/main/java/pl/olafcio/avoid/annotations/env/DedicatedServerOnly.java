package pl.olafcio.avoid.annotations.env;

import java.lang.annotation.*;

/**
 * Indicates a method only works if a dedicated server is running.<br/>
 * This doesn't include integrated (launched from the client) servers.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface DedicatedServerOnly {}
