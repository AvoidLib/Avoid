package pl.olafcio.avoid_loader.interfaces.round;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

public interface IListRoundable<E extends Enum<E>, Element, Elements extends Collection<Element>> extends IRoundable<E> {
    @Nullable
    default Elements shouldRecall() {
        return null;
    }

    @SuppressWarnings("unchecked")
    static <Stage extends Enum<Stage>, Element, Elements extends Collection<Element>, T extends IListRoundable<Stage, Element, Elements>> void runEach(
            Collection<T> roundables,
            Stage stage,
            Elements elements,
            BiConsumer<T, Element> callback,
            UnaryOperator<Elements> cloner
    ) {
        var round = roundables;
        var nextRound = new ArrayList<T>();

        while (!round.isEmpty()) {
            var clone = cloner.apply(elements);

            for (var roundable : round) {
                for (var element : clone)
                    callback.accept(roundable, element);

                if (roundable.needsAnotherRound(stage))
                    nextRound.add(roundable);

                var recall = roundable.shouldRecall();
                if (recall != null && !recall.isEmpty()) {
                    runEach(roundables, stage, recall, callback, cloner);
                }
            }

            round = (ArrayList<T>) nextRound.clone();
            nextRound.clear();
        }
    }
}
