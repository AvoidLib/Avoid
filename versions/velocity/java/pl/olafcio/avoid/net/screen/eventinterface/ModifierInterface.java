package pl.olafcio.avoid.net.screen.eventinterface;

public interface ModifierInterface {
    int getModifiers();

    default boolean holdingAlt() {
        return (this.getModifiers() & 4) != 0;
    }

    default boolean holdingShift() {
        return (this.getModifiers() & 1) != 0;
    }

    default boolean holdingControl() {
        return (this.getModifiers() & 2) != 0;
    }

    default boolean holdingControlSupport() {
        throw new UnsupportedOperationException("[ModifierInterface#holdingControlSupport] Cannot be used on Velocity");
    }
}
