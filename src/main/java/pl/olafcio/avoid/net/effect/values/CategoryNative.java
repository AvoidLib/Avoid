package pl.olafcio.avoid.net.effect.values;

import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class CategoryNative {
    @ApiStatus.Internal
    private CategoryNative() {}

    public static MobEffectCategory convertFrom(Category category) {
        return category.object;
    }
}
