package pl.olafcio.avoid_impl.net.entity_renderer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;

@FunctionalInterface
@MCEnvironment("client")
public interface LivingOp {
    void execute(
            LivingEntityRenderer<?, ?, ?> renderer,
            EntityRendererProvider.Context context,
            EntityModelSet entityModelSet,
            PlayerSkinRenderCache playerSkinRenderCache
    );
}
