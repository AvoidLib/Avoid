package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
final class AvoidRenderState<S> extends EntityRenderState {
    public final S wrappedState;

    AvoidRenderState(S wrappedState) {
        this.wrappedState = wrappedState;
    }
}
