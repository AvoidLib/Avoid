package pl.olafcio.avoid_loader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Exposes the type onto the game classpath.<br/>
 * Note that this is automatically done for mixins.
 * <br/><br/>
 * <b>WARNING: This doesn't work on NeoForge.</b>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Expose {}
