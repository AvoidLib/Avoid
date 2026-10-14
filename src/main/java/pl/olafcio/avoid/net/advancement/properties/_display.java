package pl.olafcio.avoid.net.advancement.properties;

import pl.olafcio.avoid.net.id.ID;
import pl.olafcio.avoid.net.id.OptionalID;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes the advancement's metadata.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface _display {
    String title();
    String description();

            ID icon();
    OptionalID background() default @OptionalID(namespace = "", path = "");

    boolean showToast() default true;
    boolean announceChat() default true;
    boolean hidden() default false;
}
