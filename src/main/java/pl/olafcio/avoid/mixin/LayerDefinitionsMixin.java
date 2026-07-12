package pl.olafcio.avoid.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.net.entity_layer.EntityLayersNative;
import pl.olafcio.avoid.net.entity_renderer.BakerNative;
import pl.olafcio.avoid.net.id.IdentificationNative;

import java.util.HashMap;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"), method = "createRoots")
    private static ImmutableMap<ModelLayerLocation, LayerDefinition> createRoots(
            ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> instance,
            Operation<ImmutableMap<ModelLayerLocation, LayerDefinition>> original
    ) {
        BakerNative.LOCATIONS = new HashMap<>();

        var layers = EntityLayersNative.getAndFreeze();
        for (var layer : layers) {
            var loc = new ModelLayerLocation(
                    IdentificationNative.convert(layer.id()),
                    layer.element()
            );

            ModelLayers.ALL_MODELS.add(loc);

            BakerNative.LOCATIONS.put(layer.supplier().getClass(), loc);
            BakerNative.LOCATIONS.put(layer.id() + "/" + layer.element(), loc);

            instance.put(loc, layer.supplier().make().getMinecraft());
        }

        return original.call(instance);
    }
}
