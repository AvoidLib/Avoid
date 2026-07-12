package pl.olafcio.avoid.net.entity_renderer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.id.Identification;

import java.util.ArrayList;

public abstract class LivingEntityRenderer<T extends Entity, S>
                extends EntityRenderer<T, S>
{
    private ArrayList<LivingOp> operations
      = new ArrayList<>();

    final EntityModel<S> model;
    final float shadowRadius;

    public LivingEntityRenderer(Baker baker, EntityModel<S> model, float shadowRadius) {
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    public abstract Identification getTextureLocation(S state);

    public final void addCustomHead() {
        operations.add((obj, context) -> {
            obj.addLayer(new CustomHeadLayer(obj, context.getModelSet(), context.getPlayerSkinRenderCache()));
        });
    }

    final void finishInit(net.minecraft.client.renderer.entity.LivingEntityRenderer<?, ?, ?> renderer,
                          EntityRendererProvider.Context context,
                          EntityModelSet entityModelSet,
                          PlayerSkinRenderCache playerSkinRenderCache)
    {
        for (var op : operations)
            op.execute(renderer, context, entityModelSet, playerSkinRenderCache);

        operations = null;
    }
}
