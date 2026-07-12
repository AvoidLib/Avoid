package pl.olafcio.avoid.net._3d.layer;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Experimental
public class PartDef {
    private final PartDefinition def;

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

    public PartDef addChild(String name, CubeList child, PartTransform transform) {
        PartDef part = new PartDef(child.build(), transform);

        this.def.addOrReplaceChild(name, part.def);

        return part;
    }

    public PartDef clearChild(String name) {
        return new PartDef(this.def.clearChild(name));
    }
}
