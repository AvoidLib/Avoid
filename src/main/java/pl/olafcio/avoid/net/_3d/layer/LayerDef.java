package pl.olafcio.avoid.net._3d.layer;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;

@IncompatibleChange(since = "v1.27", change = "Made abstract",
                    reason = "Implementation separation + eliminating potential environmental issues")
@ApiStatus.NonExtendable
public abstract class LayerDef {
    public final MeshDef mesh;

    public final int width;
    public final int height;

    public LayerDef(MeshDef mesh, int width, int height) {
        this.mesh = mesh;

        this.width = width;
        this.height = height;
    }
}
