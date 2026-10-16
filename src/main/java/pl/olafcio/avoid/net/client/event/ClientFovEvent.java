package pl.olafcio.avoid.net.client.event;

import org.jetbrains.annotations.ApiStatus;

/**
 * Fired when the player FOV is calculated.
 */
@ApiStatus.Experimental
public final class ClientFovEvent {
    private float fovInOptions;
    private float scale;

    public ClientFovEvent(float f, float g) {
        this.fovInOptions = f;
        this.scale = g;
    }

    /**
     * Returns the base FOV the math will be done on. (for more information, refer to {@link #scale()}).<br/>
     * This should be the value the user has set in options, except if modified by mods or the {@link #setFovInOptions(float) setFovInOptions} method.
     */
    public float fovInOptions() {
        return fovInOptions;
    }

    /**
     * Returns the current modifier of the {@link #fovInOptions()}.<br/>
     * It is used as the phase parameter in a lerp call between {@link #fovInOptions()} and {@code 1.0F}.
     */
    public float scale() {
        return scale;
    }

    /**
     * Sets the base FOV the math will be done on. (for more information, refer to {@link #scale()}).
     */
    public void setFovInOptions(float value) {
        fovInOptions = value;
    }

    /**
     * Sets the modifier of the {@link #fovInOptions()} (for more information, refer to {@link #scale()}).
     */
    public void setScale(float value) {
        scale = value;
    }
}
