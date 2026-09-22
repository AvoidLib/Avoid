package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.Discouraged;
import pl.olafcio.avoid.net._3d.model.ModelPart;

@Discouraged(reason = "This may be revamped")
@ApiStatus.Experimental
public final class EntityModelAccessor {
    @ApiStatus.Internal
    private EntityModelAccessor() {}

    public static ModelPart getPart(EntityModel<?> model) {
        return model.modelPart;
    }

    public static <S> EntityModel<S> getModel(LivingEntityRenderer<?, S> ler) {
        return ler.model;
    }

    public static float getShadowRadius(LivingEntityRenderer<?, ?> ler) {
        return ler.shadowRadius;
    }

    @Environment(EnvType.CLIENT)
    public static void finishInit(LivingEntityRenderer<?, ?> ler,  net.minecraft.client.renderer.entity.LivingEntityRenderer<?, ?, ?> renderer,
                          EntityRendererProvider.Context context,
                          EntityModelSet entityModelSet,
                          PlayerSkinRenderCache playerSkinRenderCache)
    {
        ler.finishInit(renderer, context, entityModelSet, playerSkinRenderCache);
    }
}
