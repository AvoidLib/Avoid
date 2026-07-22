package pl.olafcio.avoid.mods.annotation_processor;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.command.CommandManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to automatically register this class as a command, with the {@link CommandManager} namespace.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@NeverRemoval
public @interface AutoCommand {}
