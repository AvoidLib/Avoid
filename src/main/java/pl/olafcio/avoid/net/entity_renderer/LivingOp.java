package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;

@FunctionalInterface
@Environment(EnvType.CLIENT)
interface LivingOp {
    void execute(
            LivingEntityRenderer<?, ?, ?> renderer,
            EntityRendererProvider.Context context,
            EntityModelSet entityModelSet,
            PlayerSkinRenderCache playerSkinRenderCache
    );
}
