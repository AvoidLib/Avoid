package pl.olafcio.avoid.net.config.annotations;

import java.lang.annotation.*;

/**
 * Specifies the annotated field should be automatically read from and written to
 * when loading and saving the configuration respectively.
 *
 * @see Nested
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface YAML {
    String comment() default "";

    /**
     * Specifies this element should be multiline.
     * <br/><br/>
     * <b>Note:</b> This only applies to strings.
     */
    boolean multiline() default false;
}
