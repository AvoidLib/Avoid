package pl.olafcio.avoid.net.entity_type.properties;

import pl.olafcio.avoid.net.entity_type.properties.flag.FeatureFlag;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface _featureFlags {
    FeatureFlag[] value();
}
