package pl.olafcio.avoid.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.block.ServerEntityBlockTrampleEvent;
import pl.olafcio.avoid.net.world.WorldNative;
import pl.olafcio.avoid.net.world.block_data.BlockDataNative;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FarmBlock;turnToDirt(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"), method = "fallOn", cancellable = true)
    public void fallOn__turnToDirt(Level level, BlockState blockState, BlockPos blockPos, Entity entity, double d, CallbackInfo ci) {
        var event = new ServerEntityBlockTrampleEvent(
                EntityNative.convertFrom(entity),
                WorldNative.make(level),
                BlockDataNative.convertFrom(blockState),
                BlockPosNative.convert(blockPos)
        );

        EventManager.fire(event);

        if (event.isCancelled())
            ci.cancel();
    }
}
