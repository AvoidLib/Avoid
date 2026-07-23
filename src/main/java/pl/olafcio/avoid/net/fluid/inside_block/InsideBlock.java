package pl.olafcio.avoid.net.fluid.inside_block;

import pl.olafcio.avoid.net.entity.Entity;

import java.util.function.Consumer;

public interface InsideBlock {
    InsideBlock IGNORE = new InsideBlock() {
        @Override
        public void schedule(InsideBlockAction action) {
        }

        @Override
        public void scheduleBefore(InsideBlockAction action, Consumer<Entity> consumer) {
        }

        @Override
        public void scheduleAfter(InsideBlockAction action, Consumer<Entity> consumer) {
        }
    };

    void schedule(InsideBlockAction action);

    void scheduleBefore(InsideBlockAction action, Consumer<Entity> consumer);

    void scheduleAfter(InsideBlockAction action, Consumer<Entity> consumer);
}
