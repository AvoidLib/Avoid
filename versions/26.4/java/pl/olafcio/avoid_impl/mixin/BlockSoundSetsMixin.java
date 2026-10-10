package pl.olafcio.avoid_impl.mixin;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.block.sounds.BlockSoundSet;
import net.minecraft.world.level.block.sounds.BlockSoundSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid_impl.net.block.Blocks;

@Mixin(BlockSoundSets.class)
public class BlockSoundSetsMixin {
    @Inject(at = @At("TAIL"), method = "bootstrap")
    private static void bootstrap(BootstrapContext<BlockSoundSet> context, CallbackInfo ci) {
        for (var entry : Blocks.SOUND_SETS.entrySet())
            context.register(entry.getKey(), entry.getValue());
    }
}
