package pl.olafcio.avoid.net.entity_type.properties;

import pl.olafcio.avoid.net.entity_type.properties.attribute.Attribute;
import pl.olafcio.avoid.net.entity_type.properties.attribute.AttributeBase;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface _attributes {
    AttributeBase base();
    Attribute[] attributes();
}
