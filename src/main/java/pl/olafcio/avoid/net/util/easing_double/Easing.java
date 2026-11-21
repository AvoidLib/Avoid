package pl.olafcio.avoid.net.util.easing_double;

public interface Easing {
    double apply(double value);

    public static final Easing QUINT_IN = x -> x * x * x * x * x;
    public static final Easing QUINT_IN_OUT = x -> x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2;
}
