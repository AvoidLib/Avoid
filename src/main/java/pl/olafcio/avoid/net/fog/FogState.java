package pl.olafcio.avoid.net.fog;

/**
 * You shall write to these values within the {@link Fog} class.<br/>
 * It's kind-of a set of out parameters.
 */
public final class FogState {
    FogState() {}

    public float environmentalStart;
    public float environmentalEnd;

    public float renderDistanceStart;
    public float renderDistanceEnd;

    public float skyEnd;
    public float cloudEnd;
}
