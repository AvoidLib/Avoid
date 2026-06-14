package pl.olafcio.avoid.net.item.properties;

import pl.olafcio.avoid.net.item.values.SlotDescription;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _equippable {
    SlotDescription value();
}
