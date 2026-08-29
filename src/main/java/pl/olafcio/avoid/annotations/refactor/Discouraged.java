package pl.olafcio.avoid.annotations.refactor;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * Indicates that the annotated element should not be used,<br/>
 * because there's an explicitly high chance that it will be removed or altered in a future release.
 * <br/><br/>
 * This doesn't apply to deprecated elements.<br/>
 * In those cases, use {@link Deprecated @Deprecated(forRemoval = true)}.
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.RECORD_COMPONENT, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
@WillRefactor(aspect = "name")
@ApiStatus.Internal
@ApiStatus.NonExtendable
public @interface Discouraged {
    String reason();
}
