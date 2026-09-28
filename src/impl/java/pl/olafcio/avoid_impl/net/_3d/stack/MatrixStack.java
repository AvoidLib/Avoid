package pl.olafcio.avoid_impl.net._3d.stack;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.*;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public class MatrixStack extends pl.olafcio.avoid.net._3d.stack.MatrixStack {
    final PoseStack stack;

    public MatrixStack() {
        this.stack = new PoseStack();
    }

    MatrixStack(PoseStack stack) {
        this.stack = stack;
    }

    @Override
    public void translate(double x, double y, double z) {
        this.stack.translate(x, y, z);
    }

    @Override
    public void translate(float x, float y, float z) {
        this.stack.translate(x, y, z);
    }

    @Override
    public void translate(IVect3 vec3) {
        this.stack.translate(vec3.x(), vec3.y(), vec3.z());
    }

    @Override
    public void scale(float x, float y, float z) {
        this.stack.scale(x, y, z);
    }

    @Override
    public void multiply(Quaternionfc quaternionfc) {
        this.stack.mulPose(quaternionfc);
    }

    @Override
    public void rotateAround(Quaternionfc quaternionfc, float x, float y, float z) {
        this.stack.rotateAround(quaternionfc, x, y, z);
    }

    @Override
    public void pushMatrix() {
        stack.pushPose();
    }

    @Override
    public void popMatrix() {
        stack.popPose();
    }

    @Override
    public Matrix last() {
        return new Matrix(stack.last());
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public void setIdentity() {
        this.stack.setIdentity();
    }

    @Override
    public void multiply(Matrix4fc matrix4fc) {
        this.stack.mulPose(matrix4fc);
    }

    public static class Matrix extends pl.olafcio.avoid.net._3d.stack.MatrixStack.Matrix {
        final PoseStack.Pose pose;

        public Matrix() {
            this.pose = new PoseStack.Pose();
        }

        Matrix(PoseStack.Pose pose) {
            this.pose = pose;
        }

        @Override
        public void set(pl.olafcio.avoid.net._3d.stack.MatrixStack.Matrix pose) {
            this.pose.set(((Matrix) pose).pose);
        }

        @Override
        public Matrix4f pose() {
            return this.pose.pose();
        }

        @Override
        public Matrix3f normal() {
            return this.pose.normal();
        }

        @Override
        public Vector3f transformNormal(Vector3fc vector3fc, Vector3f vector3f) {
            return this.pose.transformNormal(vector3fc.x(), vector3fc.y(), vector3fc.z(), vector3f);
        }

        @Override
        public Vector3f transformNormal(float x, float y, float z, Vector3f vector3f) {
            return this.pose.transformNormal(x, y, z, vector3f);
        }

        @Override
        public Matrix4f translate(float x, float y, float z) {
            return this.pose.translate(x, y, z);
        }

        @Override
        public void scale(float x, float y, float z) {
            this.pose.scale(x, y, z);
        }

        @Override
        public void rotate(Quaternionfc quaternionfc) {
            this.pose.rotate(quaternionfc);
        }

        @Override
        public void rotateAround(Quaternionfc quaternionfc, float x, float y, float z) {
            this.pose.rotateAround(quaternionfc, x, y, z);
        }

        @Override
        public void setIdentity() {
            this.pose.setIdentity();
        }

        @Override
        public void mulPose(Matrix4fc matrix4fc) {
            this.pose.mulPose(matrix4fc);
        }

        @Override
        public pl.olafcio.avoid.net._3d.stack.MatrixStack.Matrix copy() {
            Matrix pose = new Matrix();
            pose.set(this);
            return pose;
        }
    }
}
