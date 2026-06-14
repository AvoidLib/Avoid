package pl.olafcio.avoid.net.entity_selector.properties;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelectorOrder {
    Enum value();

    enum Enum {
        NEAREST,
        FURTHEST,
        RANDOM
    }
}
