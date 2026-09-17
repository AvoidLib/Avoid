package pl.olafcio.avoid.mixin;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.net.block.BlocksNative;

@Mixin(BlockModelGenerators.class)
public abstract class BlockModelGeneratorsMixin {
    @Shadow
    public abstract void createTrivialCube(Block block);

    @Inject(at = @At("TAIL"), method = "run")
    public void run(CallbackInfo ci) {
        for (var block : BlocksNative.blocks)
            this.createTrivialCube(block);
    }
}
