package pl.olafcio.avoid.net.item.component.values;

import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;
import pl.olafcio.avoid.net.world.block_location.BlockLocation;
import pl.olafcio.avoid.net.world.block_location.BlockLocationNative;

import java.util.Optional;

public record LodestoneTracker(@Nullable BlockLocation target, boolean tracked) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.item.component.LodestoneTracker, LodestoneTracker>
    {
        @Override
        public LodestoneTracker transform(net.minecraft.world.item.component.LodestoneTracker value) {
            return new LodestoneTracker(
                    value.target().isEmpty()
                            ? null
                            : BlockLocationNative.convertFrom(value.target().get()),
                    value.tracked()
            );
        }

        @Override
        public net.minecraft.world.item.component.LodestoneTracker untransform(LodestoneTracker value) {
            return new net.minecraft.world.item.component.LodestoneTracker(
                    value.target == null
                            ? Optional.empty()
                            : Optional.of(BlockLocationNative.convert(value.target)),
                    value.tracked
            );
        }
    }
}
