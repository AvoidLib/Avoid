package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.client_loading.event.ClientLoadingScreenRenderMojangEventNative;

import java.util.function.Function;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {
    @Unique
    private boolean cancelled = false;

    @WrapOperation(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIIIIII)V",
                    ordinal = 0
            ),
            method = "render"
    )
    public void render__blitMojangLogo(GuiGraphics instance, Function<ResourceLocation, RenderType> function, ResourceLocation resourceLocation, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p, int q, Operation<Void> original) {
        var event = ClientLoadingScreenRenderMojangEventNative.getEvent(instance);

        EventManager.fire(event);

        //noinspection AssignmentUsedAsCondition
        if (cancelled = event.isCancelled()) {
            event.setCancelled(false);
        } else {
            original.call(instance, function, resourceLocation, i, j, f, g, k, l, m, n, o, p, q);
        }
    }

    @WrapOperation(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIIIIII)V",
                    ordinal = 1
            ),
            method = "render"
    )
    public void render__blitMojangLogo__2(GuiGraphics instance, Function<ResourceLocation, RenderType> function, ResourceLocation resourceLocation, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p, int q, Operation<Void> original) {
        if (!cancelled)
            original.call(instance, function, resourceLocation, i, j, f, g, k, l, m, n, o, p, q);
    }
}
