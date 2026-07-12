package pl.olafcio.avoid.net._3d.layer;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public record PartTransform(float x, float y, float z, float rotateX, float rotateY, float rotateZ, float scaleX, float scaleY, float scaleZ) {
    public static final PartTransform ZERO = PartTransform._trans_rot(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

    public static PartTransform translation(float x, float y, float h) {
        return PartTransform._trans_rot(x, y, h, 0.0f, 0.0f, 0.0f);
    }

    public static PartTransform rotation(float x, float y, float z) {
        return PartTransform._trans_rot(0.0f, 0.0f, 0.0f, x, y, z);
    }

    public static PartTransform _trans_rot(float x, float y, float z, float rotateX, float rotateY, float rotateZ) {
        return new PartTransform(x, y, z, rotateX, rotateY, rotateZ, 1.0f, 1.0f, 1.0f);
    }

    public PartTransform withTranslation(float offsetX, float offsetY, float offsetZ) {
        return new PartTransform(this.x + offsetX, this.y + offsetY, this.z + offsetZ, this.rotateX, this.rotateY, this.rotateZ, this.scaleX, this.scaleY, this.scaleZ);
    }

    public PartTransform withScale(float scale) {
        return new PartTransform(this.x, this.y, this.z, this.rotateX, this.rotateY, this.rotateZ, scale, scale, scale);
    }

    public PartTransform scale(float scale) {
        if (scale == 1.0f) {
            return this;
        }

        return this.scale(scale, scale, scale);
    }

    public PartTransform scale(float scaleX, float scaleY, float scaleZ) {
        return new PartTransform(
                this.x * scaleX,
                this.y * scaleY,
                this.z * scaleZ,

                this.rotateX,
                this.rotateY,
                this.rotateZ,

                this.scaleX * scaleX,
                this.scaleY * scaleY,
                this.scaleZ * scaleZ
        );
    }
}
