package pl.olafcio.avoid_impl.net._3d.layer;

import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.net._3d.layer.MeshDef;

@MCEnvironment("client")
public class LayerDef extends pl.olafcio.avoid.net._3d.layer.LayerDef {
    private final LayerDefinition def;

    public LayerDef(MeshDef mesh, int width, int height) {
        super(mesh, width, height);

        this.def = LayerDefinition.create(((pl.olafcio.avoid_impl.net._3d.layer.MeshDef) mesh).getMinecraft(), width, height);
    }

    @ApiStatus.Internal
    public LayerDefinition getMinecraft() {
        return def;
    }
}
