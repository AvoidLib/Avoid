package pl.olafcio.avoid.mods.annotation_processor;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.entity_type.EntityTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the modloader to automatically register this class as an entity, with the {@link EntityTypes} namespace.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ApiStatus.Experimental
public @interface AutoEntity {}
