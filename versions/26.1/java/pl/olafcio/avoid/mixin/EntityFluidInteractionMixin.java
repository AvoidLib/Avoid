package pl.olafcio.avoid.mixin;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mixininterface.IEntityFluidInteraction;
import pl.olafcio.avoid.net.fluid.AvoidFluid;
import pl.olafcio.avoid.net.fluid.Fluid;
import pl.olafcio.avoid.net.fluid.FluidNative;
import pl.olafcio.avoid.net.fluid.FluidsNative;

import java.util.HashMap;
import java.util.Set;

@Mixin(EntityFluidInteraction.class)
public class EntityFluidInteractionMixin implements IEntityFluidInteraction {
    private final HashMap<Fluid, EntityFluidInteraction.Tracker> avoidTrackers
            = new HashMap<>();

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(Set<TagKey<net.minecraft.world.level.material.Fluid>> fluids, CallbackInfo ci) {
        for (var fluid : FluidsNative.instances.keySet())
            avoidTrackers.put(fluid, new EntityFluidInteraction.Tracker());
    }

    @Inject(at = @At("HEAD"), method = "update", cancellable = true)
    public void update(Entity entity, boolean ignoreCurrent, CallbackInfo ci) {
        for (var value : avoidTrackers.values())
            value.reset();
    }

    @Inject(at = @At("HEAD"), method = "getTrackerFor", cancellable = true)
    public void getTrackerFor(net.minecraft.world.level.material.Fluid fluid, CallbackInfoReturnable<EntityFluidInteraction.Tracker> cir) {
        if (fluid instanceof AvoidFluid avoid)
            cir.setReturnValue(avoidTrackers.get(FluidNative.get(avoid)));
    }

    @Override
    public double avoid$getFluidHeight(final Fluid fluid) {
        var tracker = this.avoidTrackers.get(fluid);
        return tracker != null ? tracker.height : 0.0;
    }

    @Override
    public boolean avoid$isInFluid(final Fluid fluid) {
        return this.avoid$getFluidHeight(fluid) > 0.0;
    }

    @Override
    public boolean avoid$isEyeInFluid(final Fluid fluid) {
        var tracker = this.avoidTrackers.get(fluid);
        return tracker != null && tracker.eyesInside;
    }
}
