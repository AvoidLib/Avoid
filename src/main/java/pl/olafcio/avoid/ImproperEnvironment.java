package pl.olafcio.avoid;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "name")
public class ImproperEnvironment extends RuntimeException {
    public ImproperEnvironment(String message) {
        super(message);
    }
}
