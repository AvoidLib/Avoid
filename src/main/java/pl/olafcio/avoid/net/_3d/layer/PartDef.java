package pl.olafcio.avoid.net._3d.layer;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;

@IncompatibleChange(since = "v1.27", change = "Changed to an interface",
                    reason = "Implementation separation + eliminating potential environmental issues")
@ApiStatus.NonExtendable
@ApiStatus.Experimental
public interface PartDef {
    public abstract PartDef addChild(String name, CubeList child, PartTransform transform);
    public abstract PartDef clearChild(String name);
}
