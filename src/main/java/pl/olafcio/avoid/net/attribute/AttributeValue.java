package pl.olafcio.avoid.net.attribute;

import org.jetbrains.annotations.ApiStatus;

/**
 * A state of an attribute on an entity.
 * <br/><br/><hr/><br/>
 * <b>Attributes are weird forms of data:</b>
 * <ul>
 *     <li>their value is always {@link Double double},</li>
 *     <li>they have modifiers <i>(not much support in Avoid yet),</i></li>
 *     <li>they are rather persistent <i>(they don't change after spawned in vanilla).</i></li>
 * </ul>
 * <br/><hr/><br/>
 * For example, an attribute can hold the max health of an entity.<br/><br/>
 * Vanilla Minecraft doesn't change that; maybe e.g. lifesteal plugins do.
 */
@ApiStatus.NonExtendable
public abstract class AttributeValue {
    /**
     * Returns the current <i>calculated value</i> of this attribute.
     * <br/><br/>
     * <b>NOTE:</b> This isn't directly the output you specify to {@link #setValue};<br/>&emsp;&emsp;&emsp;&ensp;it also looks at modifiers.
     */
    public abstract double getValue();

    /**
     * Sets the <i>base value</i> of this attribute.
     * <br/><br/>
     * <b>NOTE:</b> This doesn't clear out modifiers, nor is it equal to what {@link #getValue} returns;<br/>&emsp;&emsp;&emsp;&ensp;a base value doesn't consider modifiers. A calculated value does.
     */
    public abstract void setValue(double value);

    /**
     * Removes all current modifiers of this attribute.
     */
    public abstract void clearModifiers();
}
