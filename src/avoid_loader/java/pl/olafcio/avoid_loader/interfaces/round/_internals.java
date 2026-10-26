package pl.olafcio.avoid_loader.interfaces.round;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

enum _internals {
    ;

    static final RecallerMap recallers
           = new RecallerMap();

    static class RecallerMap
           extends HashMap<IListRoundable<?, ?, ?>, Consumer<? extends Object[]>>
    {
        @SuppressWarnings("unchecked")
        public <E, T extends Collection<E>> Consumer<E[]> get(IListRoundable<?, E, T> key) {
            return (Consumer<E[]>) super.get(key);
        }

        @SuppressWarnings("unchecked")
        public <E, T extends Collection<E>> Consumer<E[]> put(IListRoundable<?, E, T> key, Consumer<E[]> value) {
            return (Consumer<E[]>) super.put(key, value);
        }
    }
}
