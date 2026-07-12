package pl.olafcio.avoid.net.entity_renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.id.IdentificationNative;

import java.util.function.Supplier;

@NullMarked
public final class EntityRenderers {
    @ApiStatus.Internal
    private EntityRenderers() {}

    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void register(EntityType type, Supplier<? extends EntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
        net.minecraft.client.renderer.entity.EntityRenderers.register(
                EntityTypeNative.convert(type),
                context -> new net.minecraft.client.renderer.entity.EntityRenderer<Entity, AvoidRenderState<S>>(context) {
                    private final EntityRenderer<T, S> renderer;

                    {
                        renderer = supplier.get();
                    }

                    @Override
                    @SuppressWarnings("unchecked")
                    public void extractRenderState(Entity entity, AvoidRenderState<S> state, float f) {
                        super.extractRenderState(entity, state, f);
                        renderer.render((T) EntityNative.convertFrom(entity), state.wrappedState, f);
                    }

                    @Override
                    public AvoidRenderState<S> createRenderState() {
                        return new AvoidRenderState<>(stateSupplier.get());
                    }
                }
        );
    }

    @SuppressWarnings("unchecked")
    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void registerLiving(EntityType type, Supplier<? extends LivingEntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
        var renderer = supplier.get();

        net.minecraft.client.renderer.entity.EntityRenderers.register(
                (net.minecraft.world.entity.EntityType<? extends LivingEntity>)
                EntityTypeNative.convert(type),

                context -> new net.minecraft.client.renderer.entity.LivingEntityRenderer<
                                           LivingEntity,
                                           AvoidLivingRenderState<S>,
                                           EntityModel<AvoidLivingRenderState<S>>
                                   >(context, createModel(renderer.model), renderer.shadowRadius) {
                                       @Override
                                       public Identifier getTextureLocation(AvoidLivingRenderState<S> state) {
                                           return IdentificationNative.convert(renderer.getTextureLocation(state.wrappedState));
                                       }

                                       @Override
                                       @SuppressWarnings("unchecked")
                                       public void extractRenderState(LivingEntity entity, AvoidLivingRenderState<S> state, float f) {
                                           super.extractRenderState(entity, state, f);
                                           renderer.render((T) EntityNative.convertFrom(entity), state.wrappedState, f);
                                       }

                                       @Override
                                       public AvoidLivingRenderState<S> createRenderState() {
                                           return new AvoidLivingRenderState<>(stateSupplier.get());
                                       }
                                   }
        );
    }

    private static <S> EntityModel<AvoidLivingRenderState<S>> createModel(pl.olafcio.avoid.net.entity_renderer.EntityModel<S> model) {
        return new EntityModel<AvoidLivingRenderState<S>>(modelPart) {
            @Override
            public void setupAnim(AvoidLivingRenderState<S> state) {
                model.setupAnim(state.wrappedState);
            }
        };
    }
}
