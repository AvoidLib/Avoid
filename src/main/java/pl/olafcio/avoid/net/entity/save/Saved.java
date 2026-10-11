package pl.olafcio.avoid.net.entity.save;

import java.lang.annotation.*;

/**
 * Automatically saves and loads this property in the entity save data.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Saved {}
