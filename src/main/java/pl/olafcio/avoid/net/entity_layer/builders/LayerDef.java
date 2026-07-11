package pl.olafcio.avoid.net.entity_layer.builders;

import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.jetbrains.annotations.ApiStatus;

public class LayerDef {
    public final MeshDef mesh;

    public final int width;
    public final int height;

    private final LayerDefinition def;

    public LayerDef(MeshDef mesh, int width, int height) {
        this.mesh = mesh;

        this.width = width;
        this.height = height;

        this.def = LayerDefinition.create(mesh.getMinecraft(), width, height);
    }

    @ApiStatus.Internal
    public LayerDefinition getMinecraft() {
        return def;
    }
}
