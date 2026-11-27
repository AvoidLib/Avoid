package pl.olafcio.avoid.net.entity_renderer;

import pl.olafcio.avoid.annotations.dist.MCEnvironment;
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

    @MCEnvironment("client")
    private void __resetTransforms() {
        for (ModelPart modelPart : this.modelPart.getEveryPart()) {
            modelPart.resetTransform();
        }
    }
}
