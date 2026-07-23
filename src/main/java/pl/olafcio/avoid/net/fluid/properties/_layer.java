package pl.olafcio.avoid.net.fluid.properties;

import pl.olafcio.avoid.net.fluid.properties.layer.ChunkLayer;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _layer {
    ChunkLayer value();
}
