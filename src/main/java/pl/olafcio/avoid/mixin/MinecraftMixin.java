package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;
import pl.olafcio.avoid.client.AvoidLibClient;
import pl.olafcio.avoid.mixininterface.IMinecraft;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.mods.AvoidModMeta;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.screen.event.ScreenOpenEvent;
import pl.olafcio.avoid.net.screen.event.ScreenOpenEventNative;

import java.util.HashMap;
import java.util.function.Supplier;

@Mixin(Minecraft.class)
public class MinecraftMixin implements IMinecraft {
    @Inject(at = @At("CTOR_HEAD"), method = "<init>")
    public void construct(GameConfig gameConfig, CallbackInfo ci) {
        AvoidLibClient.mc = (Minecraft) (Object) this;
    }

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

    @Unique
    private final HashMap<Class<? extends Screen>, Supplier<Screen>> OVERWRITES
            = new HashMap<>();

    @Override
    public HashMap<Class<? extends Screen>, Supplier<Screen>> avoidlib$overwrites() {
        return OVERWRITES;
    }

    @Inject(at = @At("HEAD"), method = "close")
    public void close(CallbackInfo ci) {
        var addons = AvoidModLoader.getLoadedAddons();
        for (AvoidModMeta mod : addons)
            AvoidModLoader.getLoadedAddonClass(mod)
                        .onDisable();
    }
}
