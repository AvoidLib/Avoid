package pl.olafcio.avoid.annotations;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

import java.lang.annotation.*;

/**
 * Indicates the annotated element is used internally for converting between Minecraft objects.<br/>
 * All classes that are annotated with <a color="#3887a1">@{@linkplain Native}</a>:
 * <ul>
 *   <li>should have name ending with the word {@code Native},</li>
 *   <li>must be final,</li>
 *   <li>must have a private constructor annotated with {@link ApiStatus.Internal}.</li>
 * </ul>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@WillRefactor(aspect = "name")
@ApiStatus.Internal
@ApiStatus.NonExtendable
public @interface Native {}
