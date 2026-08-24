package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class EntityRenderersNative {
    @ApiStatus.Internal
    private EntityRenderersNative() {}

    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void register(EntityType type, Supplier<? extends EntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
        net.minecraft.client.renderer.entity.EntityRenderers.register(
                EntityTypeNative.convert(type),
                context -> {
                    Renderer.lastStateSupplier = stateSupplier;

                    return new Renderer<>(context, supplier, stateSupplier);
                }
        );
    }

    @SuppressWarnings("unchecked")
    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void registerLiving(EntityType type, Function<Baker, ? extends LivingEntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
        net.minecraft.client.renderer.entity.EntityRenderers.register(
                (net.minecraft.world.entity.EntityType<? extends LivingEntity>)
                        EntityTypeNative.convert(type),

                context -> {
                    var renderer = supplier.apply(new Baker(context));

                    LivingRenderer.lastStateSupplier = stateSupplier;

                    return new LivingRenderer<>(context, renderer, stateSupplier);
                }
        );
    }

    private static <S> EntityModel<AvoidLivingRenderState<S>> createModel(pl.olafcio.avoid.net.entity_renderer.EntityModel<S> model) {
        return new EntityModel<AvoidLivingRenderState<S>>(ModelPartNative.convertFrom(model.modelPart)) {
            @Override
            public void setupAnim(AvoidLivingRenderState<S> state) {
                model.setupAnim(state.wrappedState);
            }
        };
    }

    // istg vro
    private static class LivingRenderer<T extends pl.olafcio.avoid.net.entity.Entity, S>
            extends net.minecraft.client.renderer.entity.LivingEntityRenderer<
            LivingEntity,
            AvoidLivingRenderState<S>,
            EntityModel<AvoidLivingRenderState<S>>
            >
    {

        private final LivingEntityRenderer<T, S> renderer;
        private final Supplier<S> stateSupplier;

        private static Supplier<?> lastStateSupplier;

        public LivingRenderer(EntityRendererProvider.Context context, LivingEntityRenderer<T, S> renderer, Supplier<S> stateSupplier) {
            super(context, EntityRenderersNative.createModel(renderer.model), renderer.shadowRadius);

            this.renderer = renderer;
            this.stateSupplier = stateSupplier;

            renderer.finishInit(this, context, context.getModelSet(), null);
        }

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

        @SuppressWarnings({"ConstantValue", "unchecked"})
        @Override
        public AvoidLivingRenderState<S> createRenderState() {
            return new AvoidLivingRenderState<>((stateSupplier == null ? (Supplier<S>)lastStateSupplier : stateSupplier).get());
        }
    }

    @Environment(EnvType.CLIENT)
    private static class Renderer<T extends pl.olafcio.avoid.net.entity.Entity, S> extends net.minecraft.client.renderer.entity.EntityRenderer<Entity, AvoidRenderState<S>> {
        private final EntityRenderer<T, S> renderer;
        private final Supplier<S> stateSupplier;

        private static Supplier<?> lastStateSupplier;

        public Renderer(EntityRendererProvider.Context context, Supplier<? extends EntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
            super(context);

            this.stateSupplier = stateSupplier;
            this.renderer = supplier.get();
        }

        @Override
        @SuppressWarnings("unchecked")
        public void extractRenderState(Entity entity, AvoidRenderState<S> state, float f) {
            super.extractRenderState(entity, state, f);
            renderer.render((T) EntityNative.convertFrom(entity), state.wrappedState, f);
        }

        @SuppressWarnings({"ConstantValue", "unchecked"})
        @Override
        public AvoidRenderState<S> createRenderState() {
            return new AvoidRenderState<>((stateSupplier == null ? (Supplier<S>)lastStateSupplier : stateSupplier).get());
        }
    }
}
