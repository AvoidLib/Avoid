package pl.olafcio.avoid.net.entity_type.properties;

import pl.olafcio.avoid.net.entity_type.values.Category;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface _category {
    Category value();
}
