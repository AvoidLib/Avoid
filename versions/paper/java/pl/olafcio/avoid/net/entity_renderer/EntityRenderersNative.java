package pl.olafcio.avoid.net.entity_renderer;

import pl.olafcio.avoid.annotations.dist.Dist;
import pl.olafcio.avoid.annotations.dist.OnlyIn;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net._3d.model.ModelPartNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.id.IdentificationNative;

import java.util.function.Function;
import java.util.function.Supplier;

@NullMarked
@OnlyIn(Dist.CLIENT)
@ApiStatus.Internal
public final class EntityRenderersNative {
    @ApiStatus.Internal
    private EntityRenderersNative() {}

    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void register(EntityType type, Supplier<? extends EntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
    }

    @SuppressWarnings("unchecked")
    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void registerLiving(EntityType type, Function<Baker, ? extends LivingEntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
    }
}
