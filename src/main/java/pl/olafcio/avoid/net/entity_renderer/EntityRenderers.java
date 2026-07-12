package pl.olafcio.avoid.net.entity_renderer;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.net.entity_type.EntityType;

import java.util.function.Function;
import java.util.function.Supplier;

@NullMarked
public final class EntityRenderers {
    @ApiStatus.Internal
    private EntityRenderers() {}

    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void register(EntityType type, Supplier<? extends EntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            EntityRenderersNative.register(type, supplier, stateSupplier);
    }

    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void registerLiving(EntityType type, Function<Baker, ? extends LivingEntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            EntityRenderersNative.registerLiving(type, supplier, stateSupplier);
    }
}
