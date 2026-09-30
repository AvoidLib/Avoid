package pl.olafcio.avoid.net.datapack.pack.errors;

import pl.olafcio.avoid.annotations.refactor.WillRefactor;

@WillRefactor(aspect = "name")
public class DatapackAlreadyLoaded extends RuntimeException {
    public DatapackAlreadyLoaded(String message) {
        super(message);
    }
}
