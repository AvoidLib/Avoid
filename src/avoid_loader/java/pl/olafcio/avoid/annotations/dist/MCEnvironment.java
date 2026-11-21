package pl.olafcio.avoid.annotations.dist;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.*;

/**
 * This is different from the {@code pl.olafcio.annotations.env} package in the terms of actually being excluded at runtime.
 * <br/><br/>
 * <b>For classes:</b> This throws an error when loaded with a side mismatch.<br/>
 * <b>For methods:</b> This removes the method on the other side.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface MCEnvironment {
    @MagicConstant(stringValues = {"client", "server"})
    String value();
}
