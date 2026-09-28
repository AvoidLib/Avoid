package pl.olafcio.avoid;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "name")
public class NotImplementedException extends RuntimeException {
    public NotImplementedException(String message) {
        super(message);
    }
}
