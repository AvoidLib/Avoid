package pl.olafcio.avoid.net.entity_type.properties.flag;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface FeatureFlag {
    String namespace() default "minecraft";
    String path();
}
