package pl.olafcio.avoid.mods.annotation_processor;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.keyboard.Keyboard;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyPressEvent;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyReleaseEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to create a {@link ClientKeyPressEvent} <i>(or {@link ClientKeyReleaseEvent})</i> event listener,
 * and when it fires and the key value matches, it triggers the annotated method.
 * <br/><br/>
 * (This is done with the {@link EventManager}.)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiStatus.Experimental
public @interface KeyHandler {
    @MagicConstant(valuesFromClass = Keyboard.class)
    int value();

    Trigger trigger() default Trigger.PRESS;
    Category category() default Category.__NOT_SET__;

    String displayName() default "";

    enum Trigger {
        PRESS,
        RELEASE
    }

    enum Category {
        @ApiStatus.Internal
        __NOT_SET__,

        MOVEMENT,
        MISC,
        MULTIPLAYER,
        GAMEPLAY,
        INVENTORY,
        CREATIVE,
        SPECTATOR,
        DEBUG
    }
}
