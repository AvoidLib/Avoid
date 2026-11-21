package pl.olafcio.avoid_impl.net.entity_renderer;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;

@MCEnvironment("client")
final class AvoidRenderState<S> extends EntityRenderState {
    public final S wrappedState;

    AvoidRenderState(S wrappedState) {
        this.wrappedState = wrappedState;
    }
}
