package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Friction {
    float value();
}
