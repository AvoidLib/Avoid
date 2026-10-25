package pl.olafcio.avoid_loader.interfaces.round;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public interface IRoundable<E extends Enum<E>> {
    default boolean needsAnotherRound(E stage) {
        return false;
    }

    @SuppressWarnings("unchecked")
    static <Stage extends Enum<Stage>, T extends IRoundable<Stage>> void runEach(Collection<T> roundables, Stage stage, Consumer<T> callback) {
        var nextRound = new ArrayList<T>();

        while (!roundables.isEmpty()) {
            for (var roundable : roundables) {
                callback.accept(roundable);

                if (roundable.needsAnotherRound(stage))
                    nextRound.add(roundable);
            }

            roundables = (ArrayList<T>) nextRound.clone();
            nextRound.clear();
        }
    }
}
