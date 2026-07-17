package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * <b>NOTE:</b> Only effective in Minecraft >=26.3.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _fallDistanceReduction {
    float value();
}
