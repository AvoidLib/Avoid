package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * Makes lava able to ignite this block.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _ignitedByLava {}
