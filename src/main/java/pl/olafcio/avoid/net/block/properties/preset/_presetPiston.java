package pl.olafcio.avoid.net.block.properties.preset;

import java.lang.annotation.*;

/**
 * Applies the Piston vanilla property preset.<br/>
 * Any direct property setting has priority over it.
 * <br/><br/>
 * <b>📐 What it sets (in MC 1.21.11):</b><br/>
 * <ul>
 *     <li>{@code .mapColor(MapColor.STONE)}</li>
 *     <li>{@code .strength(1.5F)}</li>
 *     <li>{@code .isRedstoneConductor(Blocks::never)} <i><u>(internal, currently unachievable otherwise)</u></i></li>
 *     <li>{@code .isSuffocating(NOT_EXTENDED_PISTON)} <i><u>(internal, currently unachievable otherwise)</u></i></li>
 *     <li>{@code .isViewBlocking(NOT_EXTENDED_PISTON)} <i><u>(internal, currently unachievable otherwise)</u></i></li>
 *     <li>{@code .pushReaction(PushReaction.BLOCK)}</li>
 * </ul>
 * @deprecated Preset usage looks bad in code. I will probably change their idea a bit.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated(forRemoval = true)
public @interface _presetPiston {}
