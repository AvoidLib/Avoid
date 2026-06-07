package pl.olafcio.avoid.net.screen.eventinterface;

import net.minecraft.client.input.InputQuirks;

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
        return (this.getModifiers() & InputQuirks.EDIT_SHORTCUT_KEY_MODIFIER) != 0;
    }
}
