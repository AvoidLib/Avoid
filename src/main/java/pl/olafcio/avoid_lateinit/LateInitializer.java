package pl.olafcio.avoid_lateinit;

import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * A callback manager.<br/>
 * <br/>
 * To append a callback, use {@link #Schedule(Runnable)}.<br/>
 * To finish collection, use {@link #Realize()}.<br/>
 * <br/>
 * All callbacks appended after collection has been finished<br/>
 * will be invoked immediately.
 */
@ApiStatus.Experimental
public class LateInitializer {
    private boolean Initialized = false;
    private final List<Runnable> Initializers
            = new ArrayList<>();

    /**
     * Appends a callback to the collection.
     * <br/><br/>
     * If {@link #Realize()} has already been called,<br/>
     * the callback will be invoked immediately.
     */
    public final void Schedule(Runnable code) {
        if (Initialized)
            code.run();
        else Initializers.add(code);
    }

    /**
     * Stops callback collection and runs all of them immediately.
     */
    protected final void Realize() {
        for (var init : Initializers)
            init.run();

        Initialized = true;
    }
}
