package pl.olafcio.avoid.net.id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalID {
    String namespace();
    String path();
}
