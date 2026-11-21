package pl.olafcio.avoid_impl.net.entity_renderer;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;

@MCEnvironment("client")
final class AvoidLivingRenderState<S> extends LivingEntityRenderState {
    public final S wrappedState;

    AvoidLivingRenderState(S wrappedState) {
        this.wrappedState = wrappedState;
    }
}
