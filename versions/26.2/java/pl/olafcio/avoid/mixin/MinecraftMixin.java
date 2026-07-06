package pl.olafcio.avoid.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.AvoidManager;
import pl.olafcio.avoid.client.AvoidLibClient;
import pl.olafcio.avoid.mods.AvoidModMeta;

import java.util.HashMap;
import java.util.function.Supplier;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(at = @At("CTOR_HEAD"), method = "<init>")
    public void construct(GameConfig gameConfig, CallbackInfo ci) {
        AvoidLibClient.mc = (Minecraft) (Object) this;
    }

    @Inject(at = @At("HEAD"), method = "close")
    public void close(CallbackInfo ci) {
        var addons = AvoidManager.getLoadedAddons();
        for (AvoidModMeta mod : addons)
            AvoidManager.getLoadedAddonClass(mod)
                    .onDisable();
    }
}
