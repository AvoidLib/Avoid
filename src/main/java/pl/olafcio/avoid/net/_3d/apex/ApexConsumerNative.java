package pl.olafcio.avoid.net._3d.apex;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class ApexConsumerNative {
    @ApiStatus.Internal
    private ApexConsumerNative() {}

    public static ApexConsumer convert(VertexConsumer consumer) {
        return new ApexConsumer() {
            @Override
            public void addApex(float x, float y, float z, int rgba, float u, float v, int u1, int v1, float normalX, float normalY, float normalZ) {
                consumer.addVertex(x, y, z, rgba, u, v, u1, v1, normalX, normalY, normalZ);
            }
        };
    }

    @SuppressWarnings("NullableProblems")
    public static VertexConsumer convertFrom(ApexConsumer consumer) {
        return new VertexConsumer() {
            @Override public VertexConsumer addVertex(float f, float g, float h) { return null; }
            @Override public VertexConsumer setColor(int i, int j, int k, int l) { return null; }
            @Override public VertexConsumer setColor(int i)                      { return null; }
            @Override public VertexConsumer setUv(float f, float g)              { return null; }
            @Override public VertexConsumer setUv1(int i, int j)                 { return null; }
            @Override public VertexConsumer setUv2(int i, int j)                 { return null; }
            @Override public VertexConsumer setNormal(float f, float g, float h) { return null; }
            @Override public VertexConsumer setLineWidth(float f)                { return null; }

            @Override
            public void addVertex(float f, float g, float h, int i, float j, float k, int l, int m, float n, float o, float p) {
                consumer.addApex(f, g, h, i, j, k, l, m, n, o, p);
            }
        };
    }
}
