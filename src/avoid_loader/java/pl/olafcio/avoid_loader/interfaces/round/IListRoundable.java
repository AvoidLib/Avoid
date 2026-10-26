package pl.olafcio.avoid_loader.interfaces.round;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static pl.olafcio.avoid_loader.interfaces.round._internals.recallers;

public interface IListRoundable<
        E extends Enum<E>,
        Element,
        Elements extends Collection<Element>
> extends IRoundable<E> {
    default void recall(Element... elements) {
        recallers.get(this).accept(elements);
    }

    @SuppressWarnings("unchecked")
    static <
            Stage extends Enum<Stage>,
            Element,
            Elements extends Collection<Element>,
            Creator extends Function<Element[], Elements>,
            T extends IListRoundable<Stage, Element, Elements>
    > void runEach(
            Collection<T> roundables,
            Stage stage,
            Elements elements,
            BiConsumer<T, Element> callback,
            UnaryOperator<Elements> cloner,
            Creator creator
    ) {
        var round = roundables;
        var nextRound = new ArrayList<T>();

        while (!round.isEmpty()) {
            var clone = cloner.apply(elements);

            for (var roundable : round) {
                Consumer<Element[]> consumer = recall -> {
                    if (recall != null && recall.length > 0) {
                        runEach(roundables, stage, creator.apply(recall), callback, cloner, creator);
                    }
                };

                for (var element : clone) {
                    recallers.put(roundable, consumer);

                    callback.accept(roundable, element);
                }

                if (roundable.needsAnotherRound(stage))
                    nextRound.add(roundable);

                recallers.remove(roundable);
            }

            round = (ArrayList<T>) nextRound.clone();
            nextRound.clear();
        }
    }
}
