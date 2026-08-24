package pl.olafcio.avoid.net.entity_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import pl.olafcio.avoid.net._3d.model.ModelPart;

@Environment(EnvType.CLIENT)
public abstract class EntityModel<S> {
    final ModelPart modelPart;

    public EntityModel(ModelPart modelPart) {
        this.modelPart = modelPart;
    }

    public void setupAnim(S state) {
        this.resetTransform();
    }

    public final void resetTransform() {
        for (ModelPart modelPart : this.modelPart.getEveryPart()) {
            modelPart.resetTransform();
        }
    }
}
