package pl.olafcio.avoid.net.entity_renderer;

import pl.olafcio.avoid.net._3d.model.ModelPart;

public abstract class EntityModel<S> {
    private final ModelPart modelPart;

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
