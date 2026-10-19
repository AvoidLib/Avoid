package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.net._3d.model.ModelPart;

public abstract class EntityModel<S> {
    final ModelPart modelPart;

    public EntityModel(ModelPart modelPart) {
        this.modelPart = modelPart;
    }

    public void setupAnim(S state) {
        this.resetTransform();
    }

    public final void resetTransform() {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            __resetTransforms();
    }

    @Environment(EnvType.CLIENT)
    private void __resetTransforms() {
        for (ModelPart modelPart : this.modelPart.getEveryPart()) {
            modelPart.resetTransform();
        }
    }
}
