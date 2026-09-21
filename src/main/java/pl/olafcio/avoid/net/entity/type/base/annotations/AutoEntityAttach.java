package pl.olafcio.avoid.net.entity.type.base.annotations;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the class is a wrapper for a Minecraft entity.
 * <br/><br/>
 * <b>NOTE:</b> This <u>DOES NOT</u> currently get processed outside Avoid internals!
 */
@ApiStatus.Internal
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoEntityAttach {}
