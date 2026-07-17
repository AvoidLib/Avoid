package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * Makes this block passthrough.<br/>This means that players will be able to walk through it.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _noCollision {}
