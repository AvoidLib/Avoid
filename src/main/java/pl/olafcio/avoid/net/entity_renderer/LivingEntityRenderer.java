package pl.olafcio.avoid.net.entity_renderer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.entity_renderer.LOCustomHead;
import pl.olafcio.avoid_impl.net.entity_renderer.LivingOp;

import java.util.ArrayList;

public abstract class LivingEntityRenderer<T extends Entity, S>
                extends EntityRenderer<T, S>
{
    @MCEnvironment("client")
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
        __addCustomHead();
    }

    @MCEnvironment("client")
    private void __addCustomHead() {
        operations.add(new LOCustomHead());
    }

    @MCEnvironment("client")
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
