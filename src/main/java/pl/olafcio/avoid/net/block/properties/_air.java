package pl.olafcio.avoid.net.block.properties;

import java.lang.annotation.*;

/**
 * Marks the block as an air block.<br/>
 * This should cause the block to not be caused by vanilla explosions.
 * <br/><br/>
 * <b>Note:</b> This may have other side effects, which I'm not aware of.<br/>
 * &emsp;&emsp;&emsp;It probably doesn't in vanilla, it's just mods doing stuff.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _air {}
