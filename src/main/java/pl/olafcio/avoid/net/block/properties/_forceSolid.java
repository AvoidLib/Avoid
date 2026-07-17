package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * Marks the block as solid.<br/><br/>
 * It is the default, but incase other properties change that, you can override that change with this annotation.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _forceSolid {}
