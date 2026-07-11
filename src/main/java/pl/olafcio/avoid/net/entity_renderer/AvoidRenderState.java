package pl.olafcio.avoid.net.entity_renderer;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

final class AvoidRenderState<S> extends EntityRenderState {
    public final S wrappedState;

    AvoidRenderState(S wrappedState) {
        this.wrappedState = wrappedState;
    }
}
