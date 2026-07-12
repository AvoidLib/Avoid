package pl.olafcio.avoid.net._3d.model;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

import java.util.Arrays;

@Native
@ApiStatus.Internal
public final class ModelPartNative {
    private ModelPartNative() {}

    public static ModelPart convert(net.minecraft.client.model.geom.ModelPart part) {
        return new ModelPart(part.cubes.stream().map(cube -> new ModelPart.Box(
                Arrays.stream(cube.polygons).map(polygon -> new ModelPart.Figure(
                        Arrays.stream(polygon.vertices()).map(vertex -> new ModelPart.Apex(
                                vertex.x(), vertex.y(), vertex.z(),
                                vertex.u(), vertex.v()
                        )).toList().toArray(ModelPart.Apex[]::new),
                        polygon.normal()
                )).toList().toArray(ModelPart.Figure[]::new),
                cube.minX, cube.minY, cube.minZ,
                cube.maxX, cube.maxY, cube.maxZ
        )), Maps.transformValues(part.children, ModelPartNative::convert));
    }
}
