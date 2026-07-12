package pl.olafcio.avoid.net._3d.stack;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.*;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public class MatrixStack {
    final PoseStack stack;

    public MatrixStack() {
        this.stack = new PoseStack();
    }

    MatrixStack(PoseStack stack) {
        this.stack = stack;
    }

    public void translate(double x, double y, double z) {
        this.stack.translate(x, y, z);
    }

    public void translate(float x, float y, float z) {
        this.stack.translate(x, y, z);
    }

    public void translate(IVect3 vec3) {
        this.stack.translate(vec3.x(), vec3.y(), vec3.z());
    }

    public void scale(float x, float y, float z) {
        this.stack.scale(x, y, z);
    }

    public void multiply(Quaternionfc quaternionfc) {
        this.stack.mulPose(quaternionfc);
    }

    public void rotateAround(Quaternionfc quaternionfc, float x, float y, float z) {
        this.stack.rotateAround(quaternionfc, x, y, z);
    }

    public void pushMatrix() {
        stack.pushPose();
    }

    public void popMatrix() {
        stack.popPose();
    }

    public Matrix last() {
        return new Matrix(stack.last());
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public void setIdentity() {
        this.stack.setIdentity();
    }

    public void multiply(Matrix4fc matrix4fc) {
        this.stack.mulPose(matrix4fc);
    }

    public static final class Matrix {
        final PoseStack.Pose pose;

        public Matrix() {
            this.pose = new PoseStack.Pose();
        }

        Matrix(PoseStack.Pose pose) {
            this.pose = pose;
        }

        public void set(Matrix pose) {
            this.pose.set(pose.pose);
        }

        public Matrix4f pose() {
            return this.pose.pose();
        }

        public Matrix3f normal() {
            return this.pose.normal();
        }

        public Vector3f transformNormal(Vector3fc vector3fc, Vector3f vector3f) {
            return this.pose.transformNormal(vector3fc.x(), vector3fc.y(), vector3fc.z(), vector3f);
        }

        public Vector3f transformNormal(float x, float y, float z, Vector3f vector3f) {
            return this.pose.transformNormal(x, y, z, vector3f);
        }

        public Matrix4f translate(float x, float y, float z) {
            return this.pose.translate(x, y, z);
        }

        public void scale(float x, float y, float z) {
            this.pose.scale(x, y, z);
        }

        public void rotate(Quaternionfc quaternionfc) {
            this.pose.rotate(quaternionfc);
        }

        public void rotateAround(Quaternionfc quaternionfc, float x, float y, float z) {
            this.pose.rotateAround(quaternionfc, x, y, z);
        }

        public void setIdentity() {
            this.pose.setIdentity();
        }

        public void mulPose(Matrix4fc matrix4fc) {
            this.pose.mulPose(matrix4fc);
        }

        public Matrix copy() {
            Matrix pose = new Matrix();
            pose.set(this);
            return pose;
        }
    }
}
