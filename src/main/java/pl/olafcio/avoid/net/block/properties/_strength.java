package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * This is a shorthand for {@link _explosionResistance @_explosionResistance} and
 *                         {@link _destroyTime @_destroyTime}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _strength {
    float value();
}
