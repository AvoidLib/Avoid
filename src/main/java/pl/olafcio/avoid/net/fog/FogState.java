package pl.olafcio.avoid.net.fog;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;

/**
 * You shall write to these values within the {@link Fog} class.<br/>
 * It's kind-of a set of out parameters.
 */
public final class FogState {
    /**
     * <b>NOTE:</b> Do not use this constructor! It may be hidden in a future update.
     */
    @Discouraged(reason = "Will be hidden")
    @ApiStatus.Internal
    public FogState() {}

    public float environmentalStart;
    public float environmentalEnd;

    public float renderDistanceStart;
    public float renderDistanceEnd;

    public float skyEnd;
    public float cloudEnd;
}
