package pl.olafcio.avoid.net.fluid.properties.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ID {
    String namespace();
    String path();
}
