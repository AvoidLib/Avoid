package pl.olafcio.avoid.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.Avoid;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(at = @At("CTOR_HEAD"), method = "<init>")
    public void construct(GameConfig gameConfig, CallbackInfo ci) {
        Avoid.mc = (Minecraft) (Object) this;
    }
}
