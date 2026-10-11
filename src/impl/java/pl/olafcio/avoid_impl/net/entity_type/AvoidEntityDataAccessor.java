package pl.olafcio.avoid_impl.net.entity_type;

import net.minecraft.network.syncher.EntityDataAccessor;
import pl.olafcio.avoid_impl.net.entity_type.sync.Controller;

public final class AvoidEntityDataAccessor<I, O> extends EntityDataAccessor<I> {
    public AvoidEntityDataAccessor(int i, Controller<O, I, ?> controller) {
        super(i, controller.serializer);
        this.controller = controller;
    }

    public final Controller<O, I, ?> controller;
    public Object lastKnownValue = null;
}
