package pl.olafcio.avoid.net.item.component.values;

import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.ItemNative;
import pl.olafcio.avoid.net.item.component.TransformingItemComponentValue;

import java.util.List;
import java.util.Optional;

public record PotDecorations(Optional<Item> back, Optional<Item> left, Optional<Item> right, Optional<Item> front) {
    public static final class Controller
           implements TransformingItemComponentValue<net.minecraft.world.level.block.entity.PotDecorations, PotDecorations>
    {
        @Override
        public PotDecorations transform(net.minecraft.world.level.block.entity.PotDecorations value) {
            return new PotDecorations(
                    value.back().map(ItemNative::convert),
                    value.left().map(ItemNative::convert),
                    value.right().map(ItemNative::convert),
                    value.front().map(ItemNative::convert)
            );
        }

        @Override
        public net.minecraft.world.level.block.entity.PotDecorations untransform(PotDecorations value) {
            return new net.minecraft.world.level.block.entity.PotDecorations(
                    value.back.map(ItemNative::convert),
                    value.left.map(ItemNative::convert),
                    value.right.map(ItemNative::convert),
                    value.front.map(ItemNative::convert)
            );
        }
    }
}
