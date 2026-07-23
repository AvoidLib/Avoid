package pl.olafcio.avoid.net.block.properties;

import pl.olafcio.avoid.net.fluid.Fluid;

import java.lang.annotation.*;

/**
 * Marks the block as a liquid (fluid).
 * <br/><br/>
 * For example, water and lava are liquids.<br/>
 * As an example of a liquid you can add within your mod, there's oil.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _liquid {
    /**
     * Set this to a fluid class to integrate with the Fluid API.<br/>
     * It makes the block actually a liquid, not just internally.
     */
    Class<? extends Fluid> fluid() default Fluid.class;
}
