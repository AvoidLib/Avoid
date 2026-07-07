package pl.olafcio.avoid.mods.annotation_processor;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.keyboard.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiStatus.Experimental
public @interface KeyHandler {
    @MagicConstant(valuesFromClass = Keyboard.class)
    int value();

    Trigger trigger() default Trigger.PRESS;

    enum Trigger {
        PRESS,
        RELEASE
    }
}
