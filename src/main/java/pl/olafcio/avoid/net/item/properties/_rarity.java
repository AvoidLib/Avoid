package pl.olafcio.avoid.net.item.properties;

import pl.olafcio.avoid.net.item.component.values.Rarity;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _rarity {
    Rarity value();
}
