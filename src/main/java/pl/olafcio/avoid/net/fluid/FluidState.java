package pl.olafcio.avoid.net.fluid;

import static net.minecraft.world.level.material.FlowingFluid.FALLING;
import static net.minecraft.world.level.material.FlowingFluid.LEVEL;

public final class FluidState {
    final net.minecraft.world.level.material.FluidState state;

    FluidState(net.minecraft.world.level.material.FluidState state) {
        this.state = state;
    }

    public boolean isSource() {
        return state.isSource();
    }

    public boolean isEmpty() {
        return state.isEmpty();
    }

    public boolean isRandomlyTicking() {
        return state.isRandomlyTicking();
    }

    public boolean isFalling() {
        return state.getValue(FALLING);
    }

    public int getLevel() {
        return state.getValue(LEVEL);
    }
}
