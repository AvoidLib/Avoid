package pl.olafcio.avoid.net._3d.layer._native;

import net.minecraft.client.model.geom.PartPose;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net._3d.layer.PartTransform;

@Native
@ApiStatus.Internal
public final class PartTransformNative {
    @ApiStatus.Internal
    private PartTransformNative() {}

    public static PartTransform convert(PartPose pose) {
        return new PartTransform(pose.x(), pose.y(), pose.z(), pose.xRot(), pose.yRot(), pose.zRot(), pose.xScale(), pose.yScale(), pose.zScale());
    }

    public static PartPose convertFrom(PartTransform pose) {
        return new PartPose(pose.x(), pose.y(), pose.z(), pose.rotateX(), pose.rotateY(), pose.rotateZ(), pose.scaleX(), pose.scaleY(), pose.scaleZ());
    }
}
