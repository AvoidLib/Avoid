package pl.olafcio.avoid.net.advancement.properties;

import pl.olafcio.avoid.net.advancement.AdvancementType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface _type {
    AdvancementType value();
}
