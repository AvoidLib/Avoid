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
                context -> new EntityAvoidRenderStateEntityRenderer<>(context, supplier, stateSupplier)
        );
    }

    @SuppressWarnings("unchecked")
    public static <T extends pl.olafcio.avoid.net.entity.Entity, S> void registerLiving(EntityType type, Function<Baker, ? extends LivingEntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
        net.minecraft.client.renderer.entity.EntityRenderers.register(
                (net.minecraft.world.entity.EntityType<? extends LivingEntity>)
                EntityTypeNative.convert(type),

                context -> {
                    var renderer = supplier.apply(new Baker(context));

                    return new LivingEntityAvoidLivingRenderStateEntityModelLivingEntityRenderer<>(context, renderer, stateSupplier);
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

    @Environment(EnvType.CLIENT)
    // istg vro
    private static class LivingEntityAvoidLivingRenderStateEntityModelLivingEntityRenderer<T extends pl.olafcio.avoid.net.entity.Entity, S> extends net.minecraft.client.renderer.entity.LivingEntityRenderer<
            LivingEntity,
            AvoidLivingRenderState<S>,
            EntityModel<AvoidLivingRenderState<S>>
    > {

        private final LivingEntityRenderer<T, S> renderer;
        private final Supplier<S> stateSupplier;

        public LivingEntityAvoidLivingRenderStateEntityModelLivingEntityRenderer(EntityRendererProvider.Context context, LivingEntityRenderer<T, S> renderer, Supplier<S> stateSupplier) {
            super(context, EntityRenderersNative.createModel(renderer.model), renderer.shadowRadius);

            this.renderer = renderer;
            this.stateSupplier = stateSupplier;

            renderer.finishInit(this, context, context.getModelSet(), context.getPlayerSkinRenderCache());
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

        @Override
        public AvoidLivingRenderState<S> createRenderState() {
            return new AvoidLivingRenderState<>(stateSupplier.get());
        }
    }

    @Environment(EnvType.CLIENT)
    private static class EntityAvoidRenderStateEntityRenderer<T extends pl.olafcio.avoid.net.entity.Entity, S> extends net.minecraft.client.renderer.entity.EntityRenderer<Entity, AvoidRenderState<S>> {
        private final EntityRenderer<T, S> renderer;
        private final Supplier<S> stateSupplier;

        public EntityAvoidRenderStateEntityRenderer(EntityRendererProvider.Context context, Supplier<? extends EntityRenderer<T, S>> supplier, Supplier<S> stateSupplier) {
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

        @Override
        public AvoidRenderState<S> createRenderState() {
            return new AvoidRenderState<>(stateSupplier.get());
        }
    }
}
