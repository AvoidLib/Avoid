package pl.olafcio.avoid.net._3d.model;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class ModelPartNative {
    private ModelPartNative() {}

    public static ModelPart convert(net.minecraft.client.model.geom.ModelPart part) {
        return new ModelPart(part);
    }

    public static net.minecraft.client.model.geom.ModelPart convertFrom(ModelPart part) {
        return part.part;
    }
}
