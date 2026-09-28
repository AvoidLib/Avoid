package pl.olafcio.avoid_impl.net._3d.layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import pl.olafcio.avoid.net._3d.layer.CubeList;
import pl.olafcio.avoid.net._3d.layer.PartTransform;

@Environment(EnvType.CLIENT)
public class MeshDef extends pl.olafcio.avoid_impl.net._3d.layer.PartDef implements pl.olafcio.avoid.net._3d.layer.MeshDef {
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

    @Override
    public PartDef addChild(String name, CubeList child, PartTransform transform) {
        PartDef part = new PartDef(((pl.olafcio.avoid_impl.net._3d.layer.CubeList) child).build(), transform);

        this.def.addOrReplaceChild(name, part.def);

        return part;
    }

    @Override
    public PartDef clearChild(String name) {
        return new pl.olafcio.avoid_impl.net._3d.layer.PartDef(this.def.clearChild(name));
    }
}
