package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.Identifier;
import net.minecraft.server.ServerAdvancementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid_impl.net.advancement.Advancements;

import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(ServerAdvancementManager.class)
public class ServerAdvancementManagerMixin {
    @WrapOperation(
            at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V"),
            method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V"
    )
    private void forEach(Map<Identifier, Advancement> instance, BiConsumer<Identifier, Advancement> action, Operation<Void> original) {
        original.call(instance, action);

        for (var entry : Advancements.ADVANCEMENTS.entrySet())
            action.accept(entry.getKey(), entry.getValue());
    }
}
