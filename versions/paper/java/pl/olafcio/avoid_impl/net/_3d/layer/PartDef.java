package pl.olafcio.avoid.net._3d.layer;

import pl.olafcio.avoid.annotations.dist.Dist;
import pl.olafcio.avoid.annotations.dist.OnlyIn;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import pl.olafcio.avoid.net._3d.layer.CubeList;
import pl.olafcio.avoid.net._3d.layer.PartTransform;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@ApiStatus.Experimental
public class PartDef implements pl.olafcio.avoid.net._3d.layer.PartDef {
    private final PartDefinition def;

    PartDef(PartDefinition def) {
        this.def = def;
    }

    PartDef(List<CubeDefinition> cubes, PartTransform transform) {
        this.def = null;
    }

    public pl.olafcio.avoid.net._3d.layer.PartDef addChild(String name, CubeList child, PartTransform transform) {
        return null;
    }

    public pl.olafcio.avoid.net._3d.layer.PartDef clearChild(String name) {
        return null;
    }
}
