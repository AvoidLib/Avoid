package pl.olafcio.avoid_impl.net._3d.layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import pl.olafcio.avoid.net._3d.layer.CubeList;
import pl.olafcio.avoid.net._3d.layer.PartTransform;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PartDef implements pl.olafcio.avoid.net._3d.layer.PartDef {
    final PartDefinition def;

    PartDef(PartDefinition def) {
        this.def = def;
    }

    PartDef(List<CubeDefinition> cubes, PartTransform transform) {
        this.def = new PartDefinition(
                cubes,
                new PartPose(
                        transform.x(), transform.y(), transform.z(),
                        transform.rotateX(), transform.rotateY(), transform.rotateZ(),
                        transform.scaleX(), transform.scaleY(), transform.scaleZ()
                )
        );
    }

    @Override
    public pl.olafcio.avoid.net._3d.layer.PartDef addChild(String name, CubeList child, PartTransform transform) {
        PartDef part = new PartDef(((pl.olafcio.avoid_impl.net._3d.layer.CubeList) child).build(), transform);

        this.def.addOrReplaceChild(name, part.def);

        return part;
    }

    @Override
    public pl.olafcio.avoid.net._3d.layer.PartDef clearChild(String name) {
        return new PartDef(this.def.clearChild(name));
    }
}
