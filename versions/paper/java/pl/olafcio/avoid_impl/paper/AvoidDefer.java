package pl.olafcio.avoid_impl.paper;

import java.util.*;
import java.util.function.Supplier;

import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.resources.ResourceKey;
import pl.olafcio.avoid_impl.Reflect;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.mods.events_stage.*;

public class AvoidDefer {
    private AvoidDefer() {}

    private static final ArrayList<Runnable> functions
                   = new ArrayList<>();

    static {
        Reflect.get(BuiltInRegistries.class, "LOADERS", Map.class).put(
                IdentificationNative.convert("avoid:fake/deferred"),
                (Supplier<?>) (() -> {
                    for (var op : functions)
                        op.run();

                    var event = new RegistryContentEvent();
                    EventManager.fire(event);

                    return null;
                })
        );
    }

    public static void defer(Runnable function) {
        functions.add(function);
    }

    private static final ArrayList<Runnable> frozen
                   = new ArrayList<>();

    static {
        var uselessRegistry = new MappedRegistry<>(
                ResourceKey.createRegistryKey(IdentificationNative.convert("avoid:fake/after_freeze")),
                Lifecycle.stable()
        );

        var registry = (MappedRegistry<?>) BuiltInRegistries.REGISTRY;
        var byId = (ObjectList<Holder.Reference<?>>) Reflect.get(MappedRegistry.class, "byId", ObjectList.class, registry);

        byId.add(new Holder.Reference<Registry<?>>(null, null, null, null) {
            @Override
            public Registry<?> value() {
                for (var op : frozen)
                    op.run();

                var event = new RegistryFrozenEvent();
                EventManager.fire(event);

                return uselessRegistry;
            }
        });
    }

    public static void afterFreeze(Runnable function) {
        frozen.add(function);
    }
}
