package pl.olafcio.avoid.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.fog.delta.TickTrackerNative;
import pl.olafcio.avoid.net.gui.event.RenderHudEvent;
import pl.olafcio.avoid.net.screen.DrawerNative;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("TAIL"), method = "render")
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!(this.minecraft.screen instanceof LevelLoadingScreen)) {
            var event = new RenderHudEvent(
                    DrawerNative.create(guiGraphics),
                    TickTrackerNative.create(deltaTracker)
            );

            EventManager.fire(event);
        }
    }
}
