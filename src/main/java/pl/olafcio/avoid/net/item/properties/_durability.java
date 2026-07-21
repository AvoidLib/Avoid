package pl.olafcio.avoid.net.item.properties;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NeverRemoval
public @interface _durability {
    int value();
}
