package pl.olafcio.avoid.mods.annotation_processor;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.screen.ScreenMarker;
import pl.olafcio.avoid.net.screen.Screens;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to automatically register the annotated method as the screen overwrite for the screen type that
 * matches the given marker, with the {@link Screens} namespace.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
public @interface OverwriteScreen {
    ScreenMarker value();
}
