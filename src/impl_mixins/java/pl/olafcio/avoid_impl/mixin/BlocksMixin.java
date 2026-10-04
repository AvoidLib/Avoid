package pl.olafcio.avoid_impl.mixin;

import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.api.Avoid;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.platform.RunningEnv;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void clinit(CallbackInfo ci) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            Avoid.INSTANCE.onEarlyInit();
    }
}
