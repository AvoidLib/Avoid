package pl.olafcio.avoid.net.entity.sync;

import java.lang.annotation.*;

/**
 * Automatically creates a synchronized entity data property out of this field, thus automatically transferring its data
 * onto clients.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NetworkSync {
    /**
     * If {@code true}, the initial value set at constructor time will not be synchronized.
     */
    boolean static_default() default false;
}
