package pl.olafcio.avoid.mods.annotation_processor;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.config.Configurations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to automatically register this class as a config, with the {@link Configurations} namespace.
 * @see pl.olafcio.avoid.net.config.AutoConfiguration net.config\AutoConfiguration
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ApiStatus.Experimental
public @interface AutoConfig {}
