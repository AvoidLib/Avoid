package pl.olafcio.avoid_impl.net._3d.stack;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class MatrixStackNative {
    @ApiStatus.Internal
    private MatrixStackNative() {}

    public static pl.olafcio.avoid.net._3d.stack.MatrixStack convert(PoseStack stack) {
        return new MatrixStack(stack);
    }

    public static PoseStack convertFrom(pl.olafcio.avoid.net._3d.stack.MatrixStack stack) {
        return ((MatrixStack) stack).stack;
    }

    public static pl.olafcio.avoid.net._3d.stack.MatrixStack.Matrix convert(PoseStack.Pose pose) {
        return new MatrixStack.Matrix(pose);
    }

    public static PoseStack.Pose convertFrom(pl.olafcio.avoid.net._3d.stack.MatrixStack.Matrix pose) {
        return ((MatrixStack.Matrix) pose).pose;
    }
}
