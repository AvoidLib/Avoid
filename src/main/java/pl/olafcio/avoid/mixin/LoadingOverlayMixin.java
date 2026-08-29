package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.client_loading.event.ClientLoadingScreenRenderMojangEvent;
import pl.olafcio.avoid.net.client_loading.event.ClientLoadingScreenRenderMojangEventNative;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {
    @Unique
    private boolean cancelled = false;

    @WrapOperation(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V",
                    ordinal = 0
            ),
            method = "render"
    )
    public void render__blitMojangLogo(GuiGraphics instance, RenderPipeline renderPipeline, Identifier identifier, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p, int q, Operation<Void> original) {
        var event = ClientLoadingScreenRenderMojangEventNative.getEvent(instance);

        EventManager.fire(event);

        //noinspection AssignmentUsedAsCondition
        if (cancelled = event.isCancelled()) {
            event.setCancelled(false);
        } else {
            original.call(instance, renderPipeline, identifier, i, j, f, g, k, l, m, n, o, p, q);
        }
    }

    @WrapOperation(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V",
                    ordinal = 1
            ),
            method = "render"
    )
    public void render__blitMojangLogo__2(GuiGraphics instance, RenderPipeline renderPipeline, Identifier identifier, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p, int q, Operation<Void> original) {
        if (!cancelled)
            original.call(instance, renderPipeline, identifier, i, j, f, g, k, l, m, n, o, p, q);
    }
}
