package pl.olafcio.avoid.net.client.event;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class ClientFovEvent {
    private float fovInOptions;
    private float scale;

    public ClientFovEvent(float f, float g) {
        this.fovInOptions = f;
        this.scale = g;
    }

    public float fovInOptions() {
        return fovInOptions;
    }

    public float scale() {
        return scale;
    }

    public void setFovInOptions(float value) {
        fovInOptions = value;
    }

    public void setScale(float value) {
        scale = value;
    }
}
