package pl.olafcio.avoid.net.item.properties;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _durability {
    int value();
}
