package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FlowingFluid.class)
public interface IFlowingFluid {
    @Invoker("beforeDestroyingBlock")
    void avoid$beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState);

    @Invoker("canConvertToSource")
    boolean avoid$canConvertToSource(ServerLevel serverLevel);

    @Invoker("getSlopeFindDistance")
    int avoid$getSlopeFindDistance(LevelReader levelReader);

    @Invoker("getDropOff")
    int avoid$getDropOff(LevelReader levelReader);
}
