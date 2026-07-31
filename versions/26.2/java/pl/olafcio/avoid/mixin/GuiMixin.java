package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mixininterface.IMinecraft;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.fog.delta.TickTrackerNative;
import pl.olafcio.avoid.net.gui.event.RenderHudEvent;
import pl.olafcio.avoid.net.screen.event.ScreenOpenEvent;
import pl.olafcio.avoid.net.screen.event.ScreenOpenEventNative;
import pl.olafcio.avoid.net.screen.DrawerNative;

import java.util.HashMap;
import java.util.function.Supplier;

@Mixin(Gui.class)
public class GuiMixin implements IMinecraft {
    @SuppressWarnings("MixinExtrasOperationParameters")
    @WrapMethod(method = "setScreen")
    public void setScreen(Screen screen, Operation<Void> original) {
        if (screen != null && OVERWRITES.containsKey(screen.getClass()))
            screen = OVERWRITES.get(screen.getClass()).get();

        ScreenOpenEvent event = new ScreenOpenEvent((IScreen) screen);
        EventManager.fire(event);

        if (event.isCancelled())
            return;

        original.call(ScreenOpenEventNative.getScreen(event));
    }

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("TAIL"), method = "extractRenderState")
    public void render(DeltaTracker deltaTracker, boolean shouldRenderLevel, boolean resourcesLoaded, CallbackInfo ci, @Local GuiGraphicsExtractor graphics) {
        if (shouldRenderLevel) {
            var event = new RenderHudEvent(
                    DrawerNative.create(graphics),
                    TickTrackerNative.create(deltaTracker)
            );

            EventManager.fire(event);
        }
    }

    @Unique
    private final HashMap<Class<? extends Screen>, Supplier<Screen>> OVERWRITES
            = new HashMap<>();

    @Override
    public HashMap<Class<? extends Screen>, Supplier<Screen>> avoidlib$overwrites() {
        return OVERWRITES;
    }
}
