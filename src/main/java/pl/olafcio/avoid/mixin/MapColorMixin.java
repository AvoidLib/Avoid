package pl.olafcio.avoid.mixin;

import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MapColor.class)
public class MapColorMixin {
    @ModifyConstant(constant = {
            @Constant(intValue = 63)
    }, method = "<init>")
    public int init__maxIndex(int constant) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(constant = {
            @Constant(stringValue = "Map colour ID must be between 0 and 63 (inclusive)")
    }, method = "<init>")
    public String init__overflowErrorMessage(String constant) {
        return "Map colour ID must be between 0 and " + Integer.MAX_VALUE + " (inclusive)";
    }
}
