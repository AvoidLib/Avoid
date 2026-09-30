package pl.olafcio.avoid.net.datapack.pack.errors;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "name")
public class DatapackFailedToToggle extends RuntimeException {
    public DatapackFailedToToggle(String message) {
        super(message);
    }
}
