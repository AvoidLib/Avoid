package pl.olafcio.avoid.net.item.properties;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.item.properties.spawnegg.ID;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NeverRemoval
public @interface _spawnEgg {
    ID entityType();
}
