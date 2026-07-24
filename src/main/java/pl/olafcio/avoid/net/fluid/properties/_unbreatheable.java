package pl.olafcio.avoid.net.fluid.properties;

import java.lang.annotation.*;

/**
 * Enables bubble mechanics for the fluid.<br/>
 * <b>NOTE:</b> This <u>doesn't</u> enable bubble columns.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _unbreatheable {}
