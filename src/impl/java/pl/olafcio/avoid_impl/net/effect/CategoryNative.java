package pl.olafcio.avoid_impl.net.effect;

import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.effect.values.Category;

@Native
@ApiStatus.Internal
public final class CategoryNative {
    @ApiStatus.Internal
    private CategoryNative() {}

    public static MobEffectCategory convertFrom(Category category) {
             if (category == Category.NEUTRAL)    return MobEffectCategory.NEUTRAL;
        else if (category == Category.BENEFICIAL) return MobEffectCategory.BENEFICIAL;
        else if (category == Category.HARMFUL)    return MobEffectCategory.HARMFUL;
        else
            throw new RuntimeException("Invalid Avoid category: '%s'".formatted(category.name()));
    }
}
