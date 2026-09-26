package pl.olafcio.avoid_impl.net.fluid;

import static net.minecraft.world.level.material.FlowingFluid.FALLING;
import static net.minecraft.world.level.material.FlowingFluid.LEVEL;

public final class FluidState extends pl.olafcio.avoid.net.fluid.FluidState {
    final net.minecraft.world.level.material.FluidState state;

    FluidState(net.minecraft.world.level.material.FluidState state) {
        this.state = state;
    }

    @Override
    public boolean isSource() {
        return state.isSource();
    }

    @Override
    public boolean isEmpty() {
        return state.isEmpty();
    }

    @Override
    public boolean isRandomlyTicking() {
        return state.isRandomlyTicking();
    }

    @Override
    public boolean isFalling() {
        return state.getValue(FALLING);
    }

    @Override
    public int getLevel() {
        return state.getValue(LEVEL);
    }
}
