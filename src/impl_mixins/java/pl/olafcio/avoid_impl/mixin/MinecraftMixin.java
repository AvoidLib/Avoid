package pl.olafcio.avoid_impl.mixin;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;
import pl.olafcio.avoid.net.client.event.ClientWindowTitleUpdateEvent;
import pl.olafcio.avoid_impl.client.AvoidLibClient;
import pl.olafcio.avoid_impl.mixininterface.IMinecraft;
import pl.olafcio.avoid_impl.mixininterface.IScreen;
import pl.olafcio.avoid.mods.AvoidModMeta;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.client.event.ClientTickEvent;
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
        for (AvoidModMeta mod : addons) {
            var main = AvoidModLoader.getLoadedAddonClass(mod);

            main.onDisable();
            main.onClientDisable();
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        EventManager.fire(ClientTickEvent.INSTANCE);
    }

    @Inject(at = @At("RETURN"), method = "createTitle", cancellable = true)
    private void createTitle(CallbackInfoReturnable<String> cir) {
        var event = new ClientWindowTitleUpdateEvent(cir.getReturnValue());

        EventManager.fire(event);

        cir.setReturnValue(event.getTitle());
    }
}
