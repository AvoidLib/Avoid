package pl.olafcio.avoid.net._3d.model;

import org.jetbrains.annotations.ApiStatus;
import org.joml.*;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.IncompatibleChange;
import pl.olafcio.avoid.net._3d.apex.ApexConsumer;
import pl.olafcio.avoid.net._3d.stack.MatrixStack;
import pl.olafcio.avoid.net.random.RandomProvider;
import pl.olafcio.avoid.net._3d.layer.PartTransform;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@IncompatibleChange(since = "v1.27", change = "Changed to an interface",
                    reason = "Implementation separation + eliminating potential environmental issues")
@ApiStatus.NonExtendable
public interface ModelPart {
    public abstract PartTransform extractTransform();
    public abstract PartTransform getDefaultTransform();
    public abstract void setDefaultTransform(PartTransform partTransform);
    public abstract void resetTransform();
    public abstract void loadTransform(PartTransform partTransform);
    public abstract boolean hasPart(String id);
    public abstract ModelPart getPart(String id);
    public abstract void pos(float x, float y, float z);
    public abstract void rotation(float x, float y, float z);
    public abstract void render(MatrixStack MatrixStack, ApexConsumer apexConsumer, int v1, int u1);
    public abstract void render(MatrixStack matrixStack, ApexConsumer apexConsumer, int v1, int u1, int rgba);
    public abstract void rotate(Quaternionf quaternionf);
    public abstract void getGuiPoints(MatrixStack matrixStack, Consumer<Vector3fc> consumer);
    public abstract void loop(MatrixStack matrixStack, Looper looper);
    public abstract void saveTransform(MatrixStack matrixStack);
    public abstract Box getRandomBox(RandomProvider randomProvider);

    @Deprecated(forRemoval = true, since = "v1.23")
    public abstract Box getRandomBox(pl.olafcio.avoid.net.block.random.RandomProvider randomProvider);

    public abstract boolean isEmpty();
    public abstract void add(Vector3f vector3f);
    public abstract void addRotate(Vector3f vector3f);
    public abstract void addScale(Vector3f vector3f);
    public abstract List<ModelPart> getEveryPart();
    public abstract Function<String, @Nullable ModelPart> getPartFinder();

    @FunctionalInterface
    interface Looper {
        void iter(MatrixStack.Matrix pose, String id, int index, Box box);
    }

    @IncompatibleChange(since = "v1.27", change = "Changed to an interface",
                        reason = "Implementation separation + eliminating potential environmental issues")
    @ApiStatus.NonExtendable
    interface Box {
        public abstract void setup(MatrixStack.Matrix pose, ApexConsumer apexConsumer, int v1, int u1, int rgba);
    }

    @IncompatibleChange(since = "v1.27", change = "Changed to an interface",
                        reason = "Implementation separation + eliminating potential environmental issues")
    @ApiStatus.NonExtendable
    interface Figure {}

    record Apex(float x, float y, float z, float u, float v) {
        public static final float SCALE_FACTOR = 16.0f;

        public Apex remap(float u, float z) {
            return new Apex(this.x, this.y, this.z, u, z);
        }

        public float worldX() {
            return this.x / SCALE_FACTOR;
        }

        public float worldY() {
            return this.y / SCALE_FACTOR;
        }

        public float worldZ() {
            return this.z / SCALE_FACTOR;
        }
    }
}
