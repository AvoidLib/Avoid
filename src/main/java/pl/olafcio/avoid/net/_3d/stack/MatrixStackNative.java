package pl.olafcio.avoid.net._3d.stack;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class MatrixStackNative {
    @ApiStatus.Internal
    private MatrixStackNative() {}

    public static MatrixStack convert(PoseStack stack) {
        return new MatrixStack(stack);
    }

    public static PoseStack convertFrom(MatrixStack stack) {
        return stack.stack;
    }

    public static MatrixStack.Matrix convert(PoseStack.Pose pose) {
        return new MatrixStack.Matrix(pose);
    }

    public static PoseStack.Pose convertFrom(MatrixStack.Matrix pose) {
        return pose.pose;
    }
}
