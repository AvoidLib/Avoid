package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * <b>NOTE:</b> Only effective in Minecraft >=26.2.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _bounceRestitution {
    float value();
}
