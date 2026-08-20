package pl.olafcio.avoid.net.effect.properties;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiStatus.Experimental
public @interface _color {
    int value();
}
