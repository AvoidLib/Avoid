package pl.olafcio.avoid.net._3d.stack;

import org.jetbrains.annotations.ApiStatus;
import org.joml.*;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;
import pl.olafcio.avoid.net.world.vect3.IVect3;

@IncompatibleChange(since = "v1.27", change = "Made abstract",
                    reason = "Implementation separation + eliminating potential environmental issues")
@ApiStatus.NonExtendable
public abstract class MatrixStack {
    public abstract void translate(double x, double y, double z);
    public abstract void translate(float x, float y, float z);
    public abstract void translate(IVect3 vec3);
    public abstract void scale(float x, float y, float z);
    public abstract void multiply(Quaternionfc quaternionfc);
    public abstract void rotateAround(Quaternionfc quaternionfc, float x, float y, float z);
    public abstract void pushMatrix();
    public abstract void popMatrix();
    public abstract Matrix last();
    public abstract boolean isEmpty();
    public abstract void setIdentity();
    public abstract void multiply(Matrix4fc matrix4fc);

    @IncompatibleChange(since = "v1.27", change = "Made abstract",
                        reason = "Implementation separation + eliminating potential environmental issues")
    @ApiStatus.NonExtendable
    public static abstract class Matrix {
        public abstract void set(Matrix pose);
        public abstract Matrix4f pose();
        public abstract Matrix3f normal();
        public abstract Vector3f transformNormal(Vector3fc vector3fc, Vector3f vector3f);
        public abstract Vector3f transformNormal(float x, float y, float z, Vector3f vector3f);
        public abstract Matrix4f translate(float x, float y, float z);
        public abstract void scale(float x, float y, float z);
        public abstract void rotate(Quaternionfc quaternionfc);
        public abstract void rotateAround(Quaternionfc quaternionfc, float x, float y, float z);
        public abstract void setIdentity();
        public abstract void mulPose(Matrix4fc matrix4fc);
        public abstract Matrix copy();
    }
}
