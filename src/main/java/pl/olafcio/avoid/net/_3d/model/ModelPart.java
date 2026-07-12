package pl.olafcio.avoid.net._3d.model;

import org.joml.*;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net._3d.ApexConsumer;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net._3d.MatrixStack;
import pl.olafcio.avoid.net.block.random.RandomProvider;
import pl.olafcio.avoid.net._3d.layer.PartTransform;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ModelPart {
    public float x;
    public float y;
    public float z;

    public float rotateX;
    public float rotateY;
    public float rotateZ;

    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float scaleZ = 1.0f;

    public boolean visible = true;
    public boolean visibleSetup;

    private final List<Box> boxlist;
    private final Map<String, ModelPart> subparts;
    private PartTransform defaultTransform = PartTransform.ZERO;

    public ModelPart(List<Box> list, Map<String, ModelPart> map) {
        this.boxlist = list;
        this.subparts = map;
    }

    public PartTransform extractTransform() {
        return PartTransform._trans_rot(this.x, this.y, this.z, this.rotateX, this.rotateY, this.rotateZ);
    }

    public PartTransform getDefaultTransform() {
        return this.defaultTransform;
    }

    public void setDefaultTransform(PartTransform PartTransform) {
        this.defaultTransform = PartTransform;
    }

    public void resetTransform() {
        this.loadTransform(this.defaultTransform);
    }

    public void loadTransform(PartTransform PartTransform) {
        this.x = PartTransform.x();
        this.y = PartTransform.y();
        this.z = PartTransform.z();

        this.rotateX = PartTransform.rotateX();
        this.rotateY = PartTransform.rotateY();
        this.rotateZ = PartTransform.rotateZ();

        this.scaleX = PartTransform.scaleX();
        this.scaleY = PartTransform.scaleY();
        this.scaleZ = PartTransform.scaleZ();
    }

    public boolean hasPart(String id) {
        return this.subparts.containsKey(id);
    }

    public ModelPart getPart(String id) {
        ModelPart modelPart = this.subparts.get(id);
        if (modelPart == null) {
            throw new NoSuchElementException("Can't find part " + id);
        }
        return modelPart;
    }

    public void pos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void rotation(float x, float y, float z) {
        this.rotateX = x;
        this.rotateY = y;
        this.rotateZ = z;
    }

    public void render(MatrixStack MatrixStack, ApexConsumer apexConsumer, int v1, int u1) {
        this.render(MatrixStack, apexConsumer, v1, u1, -1);
    }

    public void render(MatrixStack MatrixStack, ApexConsumer apexConsumer, int v1, int u1, int rgba) {
        if (!this.visible)
            return;

        if (this.boxlist.isEmpty() && this.subparts.isEmpty())
            return;

        MatrixStack.pushMatrix();
        this.saveTransform(MatrixStack);

        if (!this.visibleSetup)
            this.setup(MatrixStack.last(), apexConsumer, v1, u1, rgba);

        for (ModelPart modelPart : this.subparts.values()) {
            modelPart.render(MatrixStack, apexConsumer, v1, u1, rgba);
        }

        MatrixStack.popMatrix();
    }

    public void rotate(Quaternionf quaternionf) {
        Matrix3f matrix3f = new Matrix3f().rotationZYX(this.rotateZ, this.rotateY, this.rotateX);
        Matrix3f matrix3f2 = matrix3f.rotate(quaternionf);
        Vector3f vector3f = matrix3f2.getEulerAnglesZYX(new Vector3f());

        this.rotation(vector3f.x, vector3f.y, vector3f.z);
    }

    public void getGuiPoints(MatrixStack MatrixStack, Consumer<Vector3fc> consumer) {
        this.loop(MatrixStack, (pose, string, i, box) -> {
            for (Figure figure : box.figures) {
                for (Apex apex : figure.apexes()) {
                    float worldX = apex.worldX();
                    float worldY = apex.worldY();
                    float worldZ = apex.worldZ();

                    Vector3f vector3f = pose.pose().transformPosition(worldX, worldY, worldZ, new Vector3f());
                    consumer.accept(vector3f);
                }
            }
        });
    }

    public void loop(MatrixStack MatrixStack, Looper looper) {
        this.loop(MatrixStack, looper, "");
    }

    private void loop(MatrixStack MatrixStack, Looper looper, String id) {
        if (this.boxlist.isEmpty() && this.subparts.isEmpty()) {
            return;
        }

        MatrixStack.pushMatrix();

        this.saveTransform(MatrixStack);
        MatrixStack.Matrix pose = MatrixStack.last();

        for (int index = 0; index < this.boxlist.size(); ++index) {
            looper.iter(pose, id, index, this.boxlist.get(index));
        }

        String idSlash = id + "/";
        this.subparts.forEach((id2, modelPart) -> modelPart.loop(MatrixStack, looper, idSlash + id2));

        MatrixStack.popMatrix();
    }

    public void saveTransform(MatrixStack MatrixStack) {
        MatrixStack.translate(this.x / 16.0f, this.y / 16.0f, this.z / 16.0f);

        if (this.rotateX != 0.0f || this.rotateY != 0.0f || this.rotateZ != 0.0f) {
            MatrixStack.multiply(new Quaternionf().rotationZYX(this.rotateZ, this.rotateY, this.rotateX));
        }

        if (this.scaleX != 1.0f || this.scaleY != 1.0f || this.scaleZ != 1.0f) {
            MatrixStack.scale(this.scaleX, this.scaleY, this.scaleZ);
        }
    }

    private void setup(MatrixStack.Matrix pose, ApexConsumer apexConsumer, int v1, int u1, int rgba) {
        for (Box box : this.boxlist) {
            box.setup(pose, apexConsumer, v1, u1, rgba);
        }
    }

    public Box getRandomBox(RandomProvider randomProvider) {
        return this.boxlist.get(randomProvider.nextInt(this.boxlist.size()));
    }

    public boolean isEmpty() {
        return this.boxlist.isEmpty();
    }

    public void add(Vector3f vector3f) {
        this.x += vector3f.x();
        this.y += vector3f.y();
        this.z += vector3f.z();
    }

    public void addRotate(Vector3f vector3f) {
        this.rotateX += vector3f.x();
        this.rotateY += vector3f.y();
        this.rotateZ += vector3f.z();
    }

    public void addScale(Vector3f vector3f) {
        this.scaleX += vector3f.x();
        this.scaleY += vector3f.y();
        this.scaleZ += vector3f.z();
    }

    public List<ModelPart> getEveryPart() {
        ArrayList<ModelPart> list = new ArrayList<ModelPart>();
        list.add(this);
        this.extendTo((string, modelPart) -> list.add(modelPart));
        return List.copyOf(list);
    }

    public Function<String, @Nullable ModelPart> getPartFinder() {
        HashMap<String, ModelPart> map = new HashMap<String, ModelPart>();
        map.put("root", this);
        this.extendTo(map::putIfAbsent);
        return map::get;
    }

    private void extendTo(BiConsumer<String, ModelPart> biConsumer) {
        for (Map.Entry<String, ModelPart> entry : this.subparts.entrySet())
            biConsumer.accept(entry.getKey(), entry.getValue());

        for (ModelPart modelPart : this.subparts.values())
            modelPart.extendTo(biConsumer);
    }

    @FunctionalInterface
    public interface Looper {
        void iter(MatrixStack.Matrix pose, String id, int index, Box box);
    }

    public static class Box {
        public final Figure[] figures;

        public final float x1;
        public final float y1;
        public final float z1;

        public final float x2;
        public final float y2;
        public final float z2;

        public Box(Figure[] figures, float x1, float y1, float z1, float x2, float y2, float z2) {
            this.figures = figures;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
        }

        public Box(int a, int b, float x1, float y1, float z1, float width, float height, float depth, float revpadX, float revpadY, float revpadZ, boolean swapX, float q, float r, Set<Direction> set) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;

            this.x2 = x1 + width;
            this.y2 = y1 + height;
            this.z2 = z1 + depth;

            this.figures = new Figure[set.size()];

            float x2 = x1 + width;
            float y2 = y1 + height;
            float z2 = z1 + depth;

            x1 -= revpadX;
            y1 -= revpadY;
            z1 -= revpadZ;

            x2 += revpadX;
            y2 += revpadY;
            z2 += revpadZ;

            if (swapX) {
                float x2clone = x2;
                x2 = x1;
                x1 = x2clone;
            }

            Apex sss = new Apex(x1, y1, z1, 0.0f, 0.0f);
            Apex ess = new Apex(x2, y1, z1, 0.0f, 8.0f);
            Apex ees = new Apex(x2, y2, z1, 8.0f, 8.0f);
            Apex ses = new Apex(x1, y2, z1, 8.0f, 0.0f);
            Apex sse = new Apex(x1, y1, z2, 0.0f, 0.0f);
            Apex ese = new Apex(x2, y1, z2, 0.0f, 8.0f);
            Apex eee = new Apex(x2, y2, z2, 8.0f, 8.0f);
            Apex see = new Apex(x1, y2, z2, 8.0f, 0.0f);

            float w = a;

            float x = (float)a + depth;
            float y = (float)a + depth + width;
            float z = (float)a + depth + width + width;

            float aa = (float)a + depth + width + depth;
            float ab = (float)a + depth + width + depth + width;
            float ac = b;
            float ad = (float)b + depth;
            float ae = (float)b + depth + height;

            int index = 0;

            if (set.contains(Direction.DOWN)) {
                this.figures[index++] = new Figure(new Apex[]{ese, sse, sss, ess}, x, ac, y, ad, q, r, swapX, Direction.DOWN);
            }

            if (set.contains(Direction.UP)) {
                this.figures[index++] = new Figure(new Apex[]{ees, ses, see, eee}, y, ad, z, ac, q, r, swapX, Direction.UP);
            }

            if (set.contains(Direction.WEST)) {
                this.figures[index++] = new Figure(new Apex[]{sss, sse, see, ses}, w, ad, x, ae, q, r, swapX, Direction.WEST);
            }

            if (set.contains(Direction.NORTH)) {
                this.figures[index++] = new Figure(new Apex[]{ess, sss, ses, ees}, x, ad, y, ae, q, r, swapX, Direction.NORTH);
            }

            if (set.contains(Direction.EAST)) {
                this.figures[index++] = new Figure(new Apex[]{ese, ess, ees, eee}, y, ad, aa, ae, q, r, swapX, Direction.EAST);
            }

            if (set.contains(Direction.SOUTH)) {
                this.figures[index] = new Figure(new Apex[]{sse, ese, eee, see}, aa, ad, ab, ae, q, r, swapX, Direction.SOUTH);
            }
        }

        public void setup(MatrixStack.Matrix pose, ApexConsumer apexConsumer, int v1, int u1, int rgba) {
            Matrix4f matrix4f = pose.pose();
            Vector3f vector3f = new Vector3f();

            for (Figure figure : this.figures) {
                Vector3f vector3f2 = pose.transformNormal(figure.normal, vector3f);

                float f = vector3f2.x();
                float g = vector3f2.y();
                float h = vector3f2.z();

                for (Apex apex : figure.apexes) {
                    float l = apex.worldX();
                    float m = apex.worldY();
                    float n = apex.worldZ();

                    Vector3f vector3f3 = matrix4f.transformPosition(l, m, n, vector3f);
                    apexConsumer.addApex(vector3f3.x(), vector3f3.y(), vector3f3.z(), rgba, apex.u, apex.v, u1, v1, f, g, h);
                }
            }
        }
    }

    public record Figure(Apex[] apexes, Vector3fc normal) {
        public Figure(Apex[] apexes, float f, float g, float h, float i, float j, float k, boolean bl, Direction direction) {
            this(apexes, (bl ? Figure.mirror(direction) : direction).getVector3f());

            float l = 0.0f / j;
            float m = 0.0f / k;

            apexes[0] = apexes[0].remap(h / j - l, g / k + m);
            apexes[1] = apexes[1].remap(f / j + l, g / k + m);
            apexes[2] = apexes[2].remap(f / j + l, i / k - m);
            apexes[3] = apexes[3].remap(h / j - l, i / k - m);

            if (bl) {
                int n = apexes.length;
                for (int o = 0; o < n / 2; ++o) {
                    Apex apex = apexes[o];
                    apexes[o] = apexes[n - 1 - o];
                    apexes[n - 1 - o] = apex;
                }
            }
        }

        private static Direction mirror(Direction direction) {
            return direction.getAxis() == Direction.Axis.X ? direction.getOpposite() : direction;
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
