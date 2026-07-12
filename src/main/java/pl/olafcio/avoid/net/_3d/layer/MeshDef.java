package pl.olafcio.avoid.net._3d.layer;

import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class MeshDef extends PartDef {
    private final MeshDefinition mesh;

    public MeshDef() {
        this(new MeshDefinition());
    }

    private MeshDef(MeshDefinition mesh) {
        super(mesh.getRoot());
        this.mesh = mesh;
    }

    MeshDefinition getMinecraft() {
        return this.mesh;
    }
}
