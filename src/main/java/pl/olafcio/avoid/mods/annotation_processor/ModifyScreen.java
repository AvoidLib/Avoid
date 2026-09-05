package pl.olafcio.avoid.mods.annotation_processor;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.screen.ScreenMarker;
import pl.olafcio.avoid.net.screen.Screens;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to automatically register the annotated method as a screen modifier for the screen type that
 * matches the given marker, with the {@link Screens} namespace.
 * @see pl.olafcio.avoid.net.screen_modifier.ScreenModifier net.screen_modifier\ScreenModifier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ApiStatus.Experimental
public @interface ModifyScreen {
    ScreenMarker value();
}
