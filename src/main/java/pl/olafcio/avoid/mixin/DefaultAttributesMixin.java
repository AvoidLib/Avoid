package pl.olafcio.avoid.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.net.entity_type.EntityTypesNative;

@Mixin(DefaultAttributes.class)
public class DefaultAttributesMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"), method = "<clinit>")
    private static ImmutableMap<EntityType<? extends LivingEntity>, AttributeSupplier> build(
            ImmutableMap.Builder<EntityType<? extends LivingEntity>, AttributeSupplier> instance,
            Operation<ImmutableMap<EntityType<? extends LivingEntity>, AttributeSupplier>> original
    ) {
        var entityTypes = EntityTypesNative.getAndClean();
        for (var entry : entityTypes.entrySet()) {
            var type = entry.getKey();
            var build = entry.getValue();

            instance = instance.put(type, build.get());
        }

        return original.call(instance);
    }
}
