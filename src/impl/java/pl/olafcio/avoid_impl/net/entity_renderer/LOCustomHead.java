package pl.olafcio.avoid_impl.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;

@Environment(EnvType.CLIENT)
public final class LOCustomHead implements LivingOp {
    @Override
    public void execute(net.minecraft.client.renderer.entity.LivingEntityRenderer<?, ?, ?> obj, EntityRendererProvider.Context context, EntityModelSet entityModelSet, PlayerSkinRenderCache playerSkinRenderCache) {
        obj.addLayer(new CustomHeadLayer(obj, context.getModelSet(), context.getPlayerSkinRenderCache()));
    }
}
