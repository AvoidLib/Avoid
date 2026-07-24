package pl.olafcio.avoid.net.fluid.properties;

import java.lang.annotation.*;

/**
 * Enables gravity for the fluid.<br/><br/>
 * <b>NOTE:</b> This does not enable swimming/in-air control.<br/>
 * &emsp;&emsp;&ensp;&ensp;&nbsp;To do that, also use {@link _swimmable @_swimmable}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _gravity {}
