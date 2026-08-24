package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
final class AvoidLivingRenderState<S> extends LivingEntityRenderState {
    public final S wrappedState;

    AvoidLivingRenderState(S wrappedState) {
        this.wrappedState = wrappedState;
    }
}
