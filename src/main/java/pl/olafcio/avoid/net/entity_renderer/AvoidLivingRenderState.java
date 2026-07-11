package pl.olafcio.avoid.net.entity_renderer;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

final class AvoidLivingRenderState<S> extends LivingEntityRenderState {
    public final S wrappedState;

    AvoidLivingRenderState(S wrappedState) {
        this.wrappedState = wrappedState;
    }
}
