package pl.olafcio.avoid.net.fluid.properties;

import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayer;

import java.lang.annotation.*;

/**
 * Defines the fluid display type.<br/><br/>
 * For example, you can use {@link ChunkLayer#TRANSLUCENT} to achieve a <i>translucent (see-through)</i> fluid - like water.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _layer {
    ChunkLayer value();
}
