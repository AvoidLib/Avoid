package pl.olafcio.avoid.net.attribute;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public abstract class AttributeValue {
    public abstract double getValue();
    public abstract void setValue(double value);
    public abstract void clearModifiers();
}
