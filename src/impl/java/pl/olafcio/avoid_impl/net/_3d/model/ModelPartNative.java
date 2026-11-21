package pl.olafcio.avoid_impl.net._3d.model;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;

@Native
@MCEnvironment("client")
@ApiStatus.Internal
public final class ModelPartNative {
    private ModelPartNative() {}

    public static pl.olafcio.avoid.net._3d.model.ModelPart convert(net.minecraft.client.model.geom.ModelPart part) {
        return new ModelPart(part);
    }

    public static net.minecraft.client.model.geom.ModelPart convertFrom(pl.olafcio.avoid.net._3d.model.ModelPart part) {
        return ((ModelPart) part).part;
    }
}
