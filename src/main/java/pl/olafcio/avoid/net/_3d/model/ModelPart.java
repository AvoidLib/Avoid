package pl.olafcio.avoid.net._3d.model;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.*;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net._3d.apex.ApexConsumer;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net._3d.apex.ApexConsumerNative;
import pl.olafcio.avoid.net._3d.stack.MatrixStack;
import pl.olafcio.avoid.net._3d.layer._native.PartTransformNative;
import pl.olafcio.avoid.net._3d.stack.MatrixStackNative;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net._3d.layer.PartTransform;
import pl.olafcio.avoid.net.block.random.RandomProviderNative;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ModelPart {
    final net.minecraft.client.model.geom.ModelPart part;

    public ModelPart(net.minecraft.client.model.geom.ModelPart part) {
        this.part = part;
    }

    public PartTransform extractTransform() {
        return PartTransformNative.convert(part.storePose());
    }

    public PartTransform getDefaultTransform() {
        return PartTransformNative.convert(this.part.getInitialPose());
    }

    public void setDefaultTransform(PartTransform partTransform) {
        this.part.setInitialPose(PartTransformNative.convertFrom(partTransform));
    }

    public void resetTransform() {
        this.part.resetPose();
    }

    public void loadTransform(PartTransform partTransform) {
        this.part.loadPose(PartTransformNative.convertFrom(partTransform));
    }

    public boolean hasPart(String id) {
        return this.part.hasChild(id);
    }

    public ModelPart getPart(String id) {
        return new ModelPart(this.part.getChild(id));
    }

    public void pos(float x, float y, float z) {
        this.part.setPos(x, y, z);
    }

    public void rotation(float x, float y, float z) {
        this.part.setRotation(x, y, z);
    }

    public void render(MatrixStack MatrixStack, ApexConsumer apexConsumer, int v1, int u1) {
        this.render(MatrixStack, apexConsumer, v1, u1, -1);
    }

    public void render(MatrixStack matrixStack, ApexConsumer apexConsumer, int v1, int u1, int rgba) {
        this.part.render(MatrixStackNative.convertFrom(matrixStack), ApexConsumerNative.convertFrom(apexConsumer), v1, u1, rgba);
    }

    public void rotate(Quaternionf quaternionf) {
        this.part.rotateBy(quaternionf);
    }

    public void getGuiPoints(MatrixStack matrixStack, Consumer<Vector3fc> consumer) {
        this.part.getExtentsForGui(MatrixStackNative.convertFrom(matrixStack), consumer);
    }

    public void loop(MatrixStack matrixStack, Looper looper) {
        this.part.visit(
                MatrixStackNative.convertFrom(matrixStack),
                (pose, string, i, cube) -> looper.iter(MatrixStackNative.convert(pose), string, i, new Box(cube))
        );
    }

    public void saveTransform(MatrixStack matrixStack) {
        this.part.translateAndRotate(MatrixStackNative.convertFrom(matrixStack));
    }

    public Box getRandomBox(RandomProvider randomProvider) {
        return new Box(this.part.getRandomCube(RandomProviderNative.convert(randomProvider)));
    }

    public boolean isEmpty() {
        return this.part.isEmpty();
    }

    public void add(Vector3f vector3f) {
        this.part.offsetPos(vector3f);
    }

    public void addRotate(Vector3f vector3f) {
        this.part.offsetRotation(vector3f);
    }

    public void addScale(Vector3f vector3f) {
        this.part.offsetScale(vector3f);
    }

    public List<ModelPart> getEveryPart() {
        return this.part.getAllParts().stream()
                                      .map(ModelPart::new)
                                      .toList();
    }

    public Function<String, @Nullable ModelPart> getPartFinder() {
        var lookup = this.part.createPartLookup();
        return x -> new ModelPart(lookup.apply(x));
    }

    @FunctionalInterface
    public interface Looper {
        void iter(MatrixStack.Matrix pose, String id, int index, Box box);
    }

    public static class Box {
        private final net.minecraft.client.model.geom.ModelPart.Cube cube;

        private Box(net.minecraft.client.model.geom.ModelPart.Cube cube) {
            this.cube = cube;
        }

        public Box(int a, int b, float x1, float y1, float z1, float width, float height, float depth, float revpadX, float revpadY, float revpadZ, boolean swapX, float q, float r, Set<Direction> set) {
            this.cube = new net.minecraft.client.model.geom.ModelPart.Cube(
                    a, b,
                    x1, y1, z1,
                    width, height, depth,
                    revpadX, revpadY, revpadZ,
                    swapX,
                    q, r,
                    set.stream().map(d -> net.minecraft.core.Direction.valueOf(d.name())).collect(Collectors.toSet())
            );
        }

        public void setup(MatrixStack.Matrix pose, ApexConsumer apexConsumer, int v1, int u1, int rgba) {
            this.cube.compile(MatrixStackNative.convertFrom(pose), ApexConsumerNative.convertFrom(apexConsumer), v1, u1, rgba);
        }
    }

    public static final class Figure {
        private final net.minecraft.client.model.geom.ModelPart.Polygon polygon;

        public Figure(Apex[] apexes, float f, float g, float h, float i, float j, float k, boolean bl, Direction direction) {
            this.polygon = new net.minecraft.client.model.geom.ModelPart.Polygon(
                    Arrays.stream(apexes).map(apex -> new net.minecraft.client.model.geom.ModelPart.Vertex(
                            apex.x, apex.y, apex.z,
                            apex.u, apex.v
                    )).toArray(net.minecraft.client.model.geom.ModelPart.Vertex[]::new),
                    f, g, h, i, j, k, bl,
                    net.minecraft.core.Direction.valueOf(direction.name())
            );
        }
    }

    public record Apex(float x, float y, float z, float u, float v) {
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
