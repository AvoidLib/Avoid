package pl.olafcio.avoid_impl.net._3d.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net._3d.apex.ApexConsumer;
import pl.olafcio.avoid.net._3d.layer.PartTransform;
import pl.olafcio.avoid.net._3d.stack.MatrixStack;
import pl.olafcio.avoid_impl.net._3d.DirectionNative;
import pl.olafcio.avoid_impl.net._3d.stack.MatrixStackNative;
import pl.olafcio.avoid.net.random.RandomProvider;
import pl.olafcio.avoid_impl.net.random.RandomProviderNative;
import pl.olafcio.avoid_impl.net._3d.apex.ApexConsumerNative;
import pl.olafcio.avoid_impl.net._3d.layer._native.PartTransformNative;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class ModelPart implements pl.olafcio.avoid.net._3d.model.ModelPart {
    final net.minecraft.client.model.geom.ModelPart part;

    public ModelPart(net.minecraft.client.model.geom.ModelPart part) {
        this.part = part;
    }

    @Override
    public PartTransform extractTransform() {
        return PartTransformNative.convert(part.storePose());
    }

    @Override
    public PartTransform getDefaultTransform() {
        return PartTransformNative.convert(this.part.getInitialPose());
    }

    @Override
    public void setDefaultTransform(PartTransform partTransform) {
        this.part.setInitialPose(PartTransformNative.convertFrom(partTransform));
    }

    @Override
    public void resetTransform() {
        this.part.resetPose();
    }

    @Override
    public void loadTransform(PartTransform partTransform) {
        this.part.loadPose(PartTransformNative.convertFrom(partTransform));
    }

    @Override
    public boolean hasPart(String id) {
        return this.part.hasChild(id);
    }

    @Override
    public pl.olafcio.avoid.net._3d.model.ModelPart getPart(String id) {
        return new ModelPart(this.part.getChild(id));
    }

    @Override
    public void pos(float x, float y, float z) {
        this.part.setPos(x, y, z);
    }

    @Override
    public void rotation(float x, float y, float z) {
        this.part.setRotation(x, y, z);
    }

    @Override
    public void render(MatrixStack MatrixStack, ApexConsumer apexConsumer, int v1, int u1) {
        this.render(MatrixStack, apexConsumer, v1, u1, -1);
    }

    @Override
    public void render(MatrixStack matrixStack, ApexConsumer apexConsumer, int v1, int u1, int rgba) {
        this.part.render(MatrixStackNative.convertFrom(matrixStack), ApexConsumerNative.convertFrom(apexConsumer), v1, u1, rgba);
    }

    @Override
    public void rotate(Quaternionf quaternionf) {
        this.part.rotateBy(quaternionf);
    }

    @Override
    public void getGuiPoints(MatrixStack matrixStack, Consumer<Vector3fc> consumer) {
        this.part.getExtentsForGui(MatrixStackNative.convertFrom(matrixStack), consumer);
    }

    @Override
    public void loop(MatrixStack matrixStack, Looper looper) {
        this.part.visit(
                MatrixStackNative.convertFrom(matrixStack),
                (pose, string, i, cube) -> looper.iter(MatrixStackNative.convert(pose), string, i, new Box(cube))
        );
    }

    @Override
    public void saveTransform(MatrixStack matrixStack) {
        this.part.translateAndRotate(MatrixStackNative.convertFrom(matrixStack));
    }

    @Override
    public Box getRandomBox(RandomProvider randomProvider) {
        return new Box(this.part.getRandomCube(RandomProviderNative.convert(randomProvider)));
    }

    @Deprecated(forRemoval = true, since = "v1.23")
    @Override
    public Box getRandomBox(pl.olafcio.avoid.net.block.random.RandomProvider randomProvider) {
        return new Box(this.part.getRandomCube(RandomProviderNative.convert(randomProvider)));
    }

    @Override
    public boolean isEmpty() {
        return this.part.isEmpty();
    }

    @Override
    public void add(Vector3f vector3f) {
        this.part.offsetPos(vector3f);
    }

    @Override
    public void addRotate(Vector3f vector3f) {
        this.part.offsetRotation(vector3f);
    }

    @Override
    public void addScale(Vector3f vector3f) {
        this.part.offsetScale(vector3f);
    }

    @Override
    public List<pl.olafcio.avoid.net._3d.model.ModelPart> getEveryPart() {
        return this.part.getAllParts().stream()
                                      .map(s -> (pl.olafcio.avoid.net._3d.model.ModelPart) new ModelPart(s))
                                      .toList();
    }

    @Override
    public Function<String, pl.olafcio.avoid.net._3d.model.ModelPart> getPartFinder() {
        var lookup = this.part.createPartLookup();
        return x -> new ModelPart(lookup.apply(x));
    }

    public static class Box implements pl.olafcio.avoid.net._3d.model.ModelPart.Box {
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
                    set.stream().map(d -> DirectionNative.convertFrom(d)).collect(Collectors.toSet())
            );
        }

        @Override
        public void setup(MatrixStack.Matrix pose, ApexConsumer apexConsumer, int v1, int u1, int rgba) {
            this.cube.compile(MatrixStackNative.convertFrom(pose), ApexConsumerNative.convertFrom(apexConsumer), v1, u1, rgba);
        }
    }

    public static class Figure implements pl.olafcio.avoid.net._3d.model.ModelPart.Figure {
        private final net.minecraft.client.model.geom.ModelPart.Polygon polygon;

        public Figure(Apex[] apexes, float f, float g, float h, float i, float j, float k, boolean bl, Direction direction) {
            this.polygon = new net.minecraft.client.model.geom.ModelPart.Polygon(
                    Arrays.stream(apexes).map(apex -> new net.minecraft.client.model.geom.ModelPart.Vertex(
                            apex.x(), apex.y(), apex.z(),
                            apex.u(), apex.v()
                    )).toArray(net.minecraft.client.model.geom.ModelPart.Vertex[]::new),
                    f, g, h, i, j, k, bl,
                    DirectionNative.convertFrom(direction)
            );
        }
    }
}
