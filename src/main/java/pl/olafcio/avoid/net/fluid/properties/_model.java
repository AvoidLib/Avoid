package pl.olafcio.avoid.net.fluid.properties;

import pl.olafcio.avoid.net.fluid.properties.model.ID;

import java.lang.annotation.*;

/**
 * Sets the model of the fluid.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _model {
    ID still();
    ID flowing();
}
