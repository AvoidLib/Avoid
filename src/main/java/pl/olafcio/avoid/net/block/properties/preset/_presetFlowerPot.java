package pl.olafcio.avoid.net.block.properties.preset;

import java.lang.annotation.*;

/**
 * Applies the Flower Pot vanilla property preset.<br/>
 * Any direct property setting has priority over it.
 * <br/><br/>
 * <b>📐 What it sets (in MC 1.21.11):</b><br/>
 * <ul>
 *     <li>{@code .instabreak()}</li>
 *     <li>{@code .noOcclusion()}</li>
 *     <li>{@code .pushReaction(PushReaction.DESTROY)}</li>
 * </ul>
 * @deprecated Preset usage looks bad in code. I will probably change their idea a bit.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated(forRemoval = true)
public @interface _presetFlowerPot {}
