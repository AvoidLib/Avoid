package pl.olafcio.avoid.net.payload.field.processor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Type {
    Class<?> value();
}
