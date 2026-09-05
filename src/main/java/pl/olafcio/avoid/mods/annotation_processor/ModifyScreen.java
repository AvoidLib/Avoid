package pl.olafcio.avoid.mods.annotation_processor;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.ScreenMarker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see pl.olafcio.avoid.net.screen_modifier.ScreenModifier net.screen_modifier\ScreenModifier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ApiStatus.Experimental
public @interface ModifyScreen {
    ScreenMarker value();
}
