package pl.olafcio.avoid.net.effect.properties;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.effect.values.Category;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiStatus.Experimental
public @interface _category {
    Category value();
}
