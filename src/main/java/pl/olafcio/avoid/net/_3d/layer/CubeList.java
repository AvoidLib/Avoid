package pl.olafcio.avoid.net._3d.layer;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;

import java.util.Set;

@IncompatibleChange(since = "v1.27", change = "Changed to an interface",
                    reason = "Implementation separation + eliminating potential environmental issues")
@ApiStatus.NonExtendable
public interface CubeList {
    public abstract CubeList texOffs(int i, int j);
    public abstract CubeList mirror();
    public abstract CubeList mirror(boolean bl);
    public abstract CubeList addBox(String string, float f, float g, float h, int i, int j, int k, CubeDeformation cubeDeformation, int l, int m);
    public abstract CubeList addBox(String string, float f, float g, float h, int i, int j, int k, int l, int m);
    public abstract CubeList addBox(float f, float g, float h, float i, float j, float k);
    public abstract CubeList addBox(float f, float g, float h, float i, float j, float k, Set<Direction> set);
    public abstract CubeList addBox(String string, float f, float g, float h, float i, float j, float k);
    public abstract CubeList addBox(String string, float f, float g, float h, float i, float j, float k, CubeDeformation cubeDeformation);
    public abstract CubeList addBox(float f, float g, float h, float i, float j, float k, boolean bl);
    public abstract CubeList addBox(float f, float g, float h, float i, float j, float k, CubeDeformation cubeDeformation, float l, float m);
    public abstract CubeList addBox(float f, float g, float h, float i, float j, float k, CubeDeformation cubeDeformation);
}
